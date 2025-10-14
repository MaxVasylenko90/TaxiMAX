package dev.mvasylenko.carservice.service.handler;

import dev.mvasylenko.carservice.service.CarService;
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

@Component
@KafkaListener(
        topics = {"${car.commands.topic.name}"},
        groupId = "car-service-group")
public class CarCommandsHandler {
    private static final Logger LOG = LoggerFactory.getLogger(CarCommandsHandler.class);
    private final CarService carService;

    public CarCommandsHandler(CarService carService) {
        this.carService = carService;
    }

    @KafkaHandler
    public void handleCommand(@Payload ConfirmCarReservationCommand command) {
        carService.changeCarRentalStatus(command.getCarId(), CarRentalStatus.APPROVED);
    }

    @KafkaHandler
    public void handleCommand(@Payload CancelCarReservationCommand command,
                              @Header(KafkaHeaders.RECEIVED_KEY) String kafkaMessageKey) {
        var carId = command.getCarId();
        carService.releaseCar(carId);
        carService.changeCarRentalStatus(carId, CarRentalStatus.REJECTED);
    }
}
