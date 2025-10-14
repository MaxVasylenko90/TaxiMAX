package dev.mvasylenko.carservice.saga;

import dev.mvasylenko.carservice.service.CarRentalHistoryService;
import dev.mvasylenko.core.commands.*;
import dev.mvasylenko.core.enums.CarRentalStatus;
import dev.mvasylenko.core.events.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics={
        "${car.events.topic.name}",
        "${payment.events.topic.name}",
        "${driver.events.topic.name}"})
public class CarSaga {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final CarRentalHistoryService carRentalHistoryService;
    private final String paymentCommandsTopicName;
    private final String driverCommandsTopicName;
    private final String carCommandsTopicName;


    public CarSaga(KafkaTemplate<String, Object> kafkaTemplate,
                   CarRentalHistoryService carRentalHistoryService,
                   @Value("${payment.commands.topic.name}") String paymentCommandsTopicName,
                   @Value("${driver.commands.topic.name}") String driverCommandsTopicName,
                   @Value("${car.commands.topic.name}") String carCommandsTopicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.carRentalHistoryService = carRentalHistoryService;
        this.paymentCommandsTopicName = paymentCommandsTopicName;
        this.driverCommandsTopicName = driverCommandsTopicName;
        this.carCommandsTopicName = carCommandsTopicName;
    }

    @KafkaHandler
    public void handleEvent(@Payload CarSuccessfullyReservedEvent event) {
        CreatePaymentCommand command = new CreatePaymentCommand(
                event.getDriverId(),
                event.getCarRentPrice()
        );

        kafkaTemplate.send(paymentCommandsTopicName, event.getCarId().toString(), command);

        carRentalHistoryService.add(event.getCarId(),event.getCarId(), CarRentalStatus.RESERVED);
    }

    @KafkaHandler
    public void handleEvent(@Payload PaymentCreatedEvent event,
                            @Header(KafkaHeaders.RECEIVED_KEY) String kafkaMessageKey) {
        WithdrawDriverAmountCommand command = new WithdrawDriverAmountCommand(event.getSenderId(),
                event.getCarRentPrice(), event.getPaymentId());

        kafkaTemplate.send(driverCommandsTopicName, kafkaMessageKey, command);
    }

    @KafkaHandler
    public void handleEvent(@Payload SuccessfullWithdrawEvent event,
                            @Header(KafkaHeaders.RECEIVED_KEY) String kafkaMessageKey) {
        CommitPaymentCommand command = new CommitPaymentCommand(event.getPaymentId());
        kafkaTemplate.send(paymentCommandsTopicName, kafkaMessageKey, command);
    }

    @KafkaHandler
    public void handleEvent(@Payload FailedWithdrawEvent event,
                            @Header(KafkaHeaders.RECEIVED_KEY) String kafkaMessageKey) {
        RollbackPaymentCommand command = new RollbackPaymentCommand(event.getPaymentId(), event.getReason());
        kafkaTemplate.send(paymentCommandsTopicName, kafkaMessageKey, command);
    }

    @KafkaHandler
    public void handleEvent(@Payload PaymentCommitedEvent event,
                            @Header(KafkaHeaders.RECEIVED_KEY) String kafkaMessageKey) {
       ConfirmCarReservationCommand command = new ConfirmCarReservationCommand(event.getCarId());
       kafkaTemplate.send(carCommandsTopicName, kafkaMessageKey, command);
    }

    @KafkaHandler
    public void handleEvent(@Payload PaymentCancelledEvent event,
                            @Header(KafkaHeaders.RECEIVED_KEY) String kafkaMessageKey) {
        CancelCarReservationCommand command = new CancelCarReservationCommand(event.getCarId());
        kafkaTemplate.send(carCommandsTopicName, kafkaMessageKey, command);
    }
}
