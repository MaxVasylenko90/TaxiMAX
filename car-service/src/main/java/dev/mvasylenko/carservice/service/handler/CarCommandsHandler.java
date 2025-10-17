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
        handle(command.getCommandId(), () -> {
            carService.changeCarRentalStatus(command.getCarId(), CarRentalStatus.APPROVED, kafkaMessageKey);
        });
    }

    @KafkaHandler
    public void handleCommand(@Payload CancelCarReservationCommand command,
                              @Header(KafkaHeaders.RECEIVED_KEY) String kafkaMessageKey) {
        handle(command.getCommandId(), () -> {
            var carId = command.getCarId();
            carService.releaseCar(carId);
            carService.changeCarRentalStatus(carId, CarRentalStatus.REJECTED, kafkaMessageKey, command.getSenderId());
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
