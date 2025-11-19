package dev.mvasylenko.carservice.saga;

import dev.mvasylenko.carservice.service.IdempotencyService;
import dev.mvasylenko.core.commands.*;
import dev.mvasylenko.core.events.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = {
        "${car.events.topic.name}",
        "${payment.events.topic.name}",
        "${driver.events.topic.name}"})
public class CarSaga {
    private static final Logger LOG = LoggerFactory.getLogger(CarSaga.class);
    private final IdempotencyService idempotencyService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String paymentCommandsTopicName;
    private final String driverCommandsTopicName;
    private final String carCommandsTopicName;

    public CarSaga(IdempotencyService idempotencyService, KafkaTemplate<String, Object> kafkaTemplate,
                   @Value("${payment.commands.topic.name}") String paymentCommandsTopicName,
                   @Value("${driver.commands.topic.name}") String driverCommandsTopicName,
                   @Value("${car.commands.topic.name}") String carCommandsTopicName) {
        this.idempotencyService = idempotencyService;
        this.kafkaTemplate = kafkaTemplate;
        this.paymentCommandsTopicName = paymentCommandsTopicName;
        this.driverCommandsTopicName = driverCommandsTopicName;
        this.carCommandsTopicName = carCommandsTopicName;
    }

    @KafkaHandler
    public void handleEvent(@Payload CarSuccessfullyReservedEvent event,
                            @Header(KafkaHeaders.RECEIVED_KEY) String kafkaMessageKey,
                            Acknowledgment acknowledgment) {
        handle(event.getEventId().toString(), acknowledgment, () -> {
            CreatePaymentCommand command = new CreatePaymentCommand(event.getDriverId(), event.getCarRentPrice());
            kafkaTemplate.send(paymentCommandsTopicName, kafkaMessageKey, command);
        });
    }

    @KafkaHandler
    public void handleEvent(@Payload PaymentCreatedEvent event,
                            @Header(KafkaHeaders.RECEIVED_KEY) String kafkaMessageKey,
                            Acknowledgment acknowledgment) {
        handle(event.getEventId().toString(), acknowledgment, () -> {
            WithdrawDriverAmountCommand command = new WithdrawDriverAmountCommand(event.getSenderId(),
                    event.getCarRentPrice(), event.getPaymentId());
            kafkaTemplate.send(driverCommandsTopicName, kafkaMessageKey, command);
        });
    }

    @KafkaHandler
    public void handleEvent(@Payload SuccessfulWithdrawEvent event,
                            @Header(KafkaHeaders.RECEIVED_KEY) String kafkaMessageKey,
                            Acknowledgment acknowledgment) {
        handle(event.getEventId().toString(), acknowledgment, () -> {
            CommitPaymentCommand command = new CommitPaymentCommand(event.getPaymentId());
            kafkaTemplate.send(paymentCommandsTopicName, kafkaMessageKey, command);
        });
    }

    @KafkaHandler
    public void handleEvent(@Payload FailedWithdrawEvent event,
                            @Header(KafkaHeaders.RECEIVED_KEY) String kafkaMessageKey,
                            Acknowledgment acknowledgment) {
        handle(event.getEventId().toString(), acknowledgment, () -> {
            RollbackPaymentCommand command = new RollbackPaymentCommand(event.getPaymentId(),
                    event.getReason(), event.getSenderId());
            kafkaTemplate.send(paymentCommandsTopicName, kafkaMessageKey, command);
        });
    }

    @KafkaHandler
    public void handleEvent(@Payload PaymentCommitedEvent event,
                            @Header(KafkaHeaders.RECEIVED_KEY) String kafkaMessageKey,
                            Acknowledgment acknowledgment) {
        handle(event.getEventId().toString(), acknowledgment, () -> {
            ConfirmCarReservationCommand command = new ConfirmCarReservationCommand(event.getCarId());
            kafkaTemplate.send(carCommandsTopicName, kafkaMessageKey, command);
        });
    }

    @KafkaHandler
    public void handleEvent(@Payload PaymentCancelledEvent event,
                            @Header(KafkaHeaders.RECEIVED_KEY) String kafkaMessageKey,
                            Acknowledgment acknowledgment) {
        handle(event.getEventId().toString(), acknowledgment, () -> {
            CancelCarReservationCommand command = new CancelCarReservationCommand(event.getCarId(), event.getSenderId());
            kafkaTemplate.send(carCommandsTopicName, kafkaMessageKey, command);
        });
    }

    private void handle(String id, Acknowledgment acknowledgment, Runnable runnable) {
        final String key = "event:" + id;
        if (!idempotencyService.tryAcquire(key)) {
            LOG.info("Event {} already processed or in progress. Skipping...", id);
            acknowledgment.acknowledge();
            return;
        }
        try {
            runnable.run();
            idempotencyService.markAsDone(key);
            acknowledgment.acknowledge();
        } catch (Exception e) {
            LOG.error("Error processing event {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
