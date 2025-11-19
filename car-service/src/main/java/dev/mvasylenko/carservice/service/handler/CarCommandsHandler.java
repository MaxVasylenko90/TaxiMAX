package dev.mvasylenko.carservice.service.handler;

import dev.mvasylenko.carservice.service.CarService;
import dev.mvasylenko.carservice.service.IdempotencyService;
import dev.mvasylenko.core.commands.CancelCarReservationCommand;
import dev.mvasylenko.core.commands.ConfirmCarReservationCommand;
import dev.mvasylenko.core.enums.CarRentalStatus;
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
@KafkaListener(topics = {"${car.commands.topic.name}"})
public class CarCommandsHandler {
    private static final Logger LOG = LoggerFactory.getLogger(CarCommandsHandler.class);
    private final IdempotencyService idempotencyService;
    private final CarService carService;

    public CarCommandsHandler(IdempotencyService idempotencyService, CarService carService) {
        this.idempotencyService = idempotencyService;
        this.carService = carService;
    }

    @KafkaHandler
    public void handleCommand(@Payload ConfirmCarReservationCommand command,
                               @Header(KafkaHeaders.RECEIVED_KEY) String kafkaMessageKey) {
        handle(command.getCommandId().toString(), () -> {
            carService.changeCarRentalStatus(command.getCarId(), CarRentalStatus.APPROVED, kafkaMessageKey);
        });
    }

    @KafkaHandler
    public void handleCommand(@Payload CancelCarReservationCommand command,
                              @Header(KafkaHeaders.RECEIVED_KEY) String kafkaMessageKey) {
        handle(command.getCommandId().toString(), () -> {
            carService.releaseCar(command.getCarId(), CarRentalStatus.REJECTED, kafkaMessageKey, command.getSenderId());
        });
    }

    private void handle(String id, Runnable runnable) {
        final String key = "command:" + id;
        if (!idempotencyService.tryAcquire(key)) {
            LOG.info("Command with id={} has already processed! Skipping.", id);
            return;
        }
        try {
            runnable.run();
            idempotencyService.markAsDone(key);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }
}
