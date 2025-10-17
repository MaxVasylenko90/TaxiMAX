package dev.mvasylenko.driverservice.service.handler;

import dev.mvasylenko.core.commands.AssignCarToDriverCommand;
import dev.mvasylenko.core.commands.WithdrawDriverAmountCommand;
import dev.mvasylenko.driverservice.service.DriverService;
import dev.mvasylenko.driverservice.service.IdempotencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@KafkaListener(topics = {"${driver.commands.topic.name}"})
public class DriverCommandsHandler {
    private final Logger LOG = LoggerFactory.getLogger(DriverCommandsHandler.class);
    private final DriverService driverService;
    private final IdempotencyService idempotencyService;

    public DriverCommandsHandler(DriverService driverService, IdempotencyService idempotencyService) {

        this.driverService = driverService;
        this.idempotencyService = idempotencyService;
    }

    @KafkaHandler
    public void handleCommand(@Payload WithdrawDriverAmountCommand command,
                              @Header(KafkaHeaders.RECEIVED_KEY) String kafkaMessageKey) {
        handle(command.getCommandId(), () -> {
            driverService.withdraw(command, kafkaMessageKey);
        });
    }

    @KafkaHandler
    public void handleCommand(@Payload AssignCarToDriverCommand command) {
        handle(command.getCommandId(), () -> {
            driverService.assignCarForDriver(command.getCarId(), command.getDriverId());
        });
    }

    private void handle(UUID commandId, Runnable runnable) {
        if (idempotencyService.isCommandProcessed(commandId)) {
            LOG.info("Command with id={} has already processed! Skipping.", commandId);
            return;
        }
        try {
            runnable.run();
            idempotencyService.markCommandAsProcessed(commandId);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }
}
