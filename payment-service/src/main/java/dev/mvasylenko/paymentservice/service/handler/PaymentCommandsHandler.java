package dev.mvasylenko.paymentservice.service.handler;

import dev.mvasylenko.core.commands.CommitPaymentCommand;
import dev.mvasylenko.core.commands.CreatePaymentCommand;
import dev.mvasylenko.core.commands.RollbackPaymentCommand;
import dev.mvasylenko.core.dto.PaymentDto;
import dev.mvasylenko.core.enums.PaymentStatus;
import dev.mvasylenko.core.events.PaymentCancelledEvent;
import dev.mvasylenko.core.events.PaymentCommitedEvent;
import dev.mvasylenko.core.events.PaymentCreatedEvent;
import dev.mvasylenko.paymentservice.service.IdempotencyService;
import dev.mvasylenko.paymentservice.service.PaymentHistoryService;
import dev.mvasylenko.paymentservice.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@KafkaListener(topics = {"${payment.commands.topic.name}"})
public class PaymentCommandsHandler {
    private static final Logger LOG = LoggerFactory.getLogger(PaymentCommandsHandler.class);
    private final IdempotencyService idempotencyService;
    private final PaymentService paymentService;
    private final PaymentHistoryService paymentHistoryService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String paymentEventsTopicName;

    public PaymentCommandsHandler(IdempotencyService idempotencyService, PaymentService paymentService,
                                  PaymentHistoryService paymentHistoryService,
                                  KafkaTemplate<String, Object> kafkaTemplate,
                                  @Value("${payment.events.topic.name}") String paymentEventsTopicName) {
        this.idempotencyService = idempotencyService;
        this.paymentService = paymentService;
        this.paymentHistoryService = paymentHistoryService;
        this.kafkaTemplate = kafkaTemplate;
        this.paymentEventsTopicName = paymentEventsTopicName;
    }

    @KafkaHandler
    public void handleCommand(@Payload CreatePaymentCommand command,
                              @Header(KafkaHeaders.RECEIVED_KEY) String kafkaMessageKey) {
        handle(command.getCommandId(), () -> {
            PaymentDto payment = paymentService.processRentPayment(command.getDriverId(), command.getCarRentPrice());

            PaymentCreatedEvent event = new PaymentCreatedEvent(payment.getId(), payment.getSenderId(), payment.getAmount());
            kafkaTemplate.send(paymentEventsTopicName, kafkaMessageKey, event);

            paymentHistoryService.add(payment.getId(), payment.getStatus());
        });
    }

    @KafkaHandler
    public void handleCommand(@Payload CommitPaymentCommand command,
                              @Header(KafkaHeaders.RECEIVED_KEY) String kafkaMessageKey) {
        handle(command.getCommandId(), () -> {
            paymentService.changePaymentStatus(command.getPaymentId(), PaymentStatus.COMPLETED);

            PaymentCommitedEvent event = new PaymentCommitedEvent(UUID.fromString(kafkaMessageKey));
            kafkaTemplate.send(paymentEventsTopicName, kafkaMessageKey, event);
        });
    }

    @KafkaHandler
    public void handleCommand(@Payload RollbackPaymentCommand command,
                              @Header(KafkaHeaders.RECEIVED_KEY) String kafkaMessageKey) {
        handle(command.getCommandId(), () -> {
            paymentService.changePaymentStatus(command.getPaymentId(), PaymentStatus.CANCELLED);

            PaymentCancelledEvent event = new PaymentCancelledEvent(UUID.fromString(kafkaMessageKey),
                    command.getSenderId());
            kafkaTemplate.send(paymentEventsTopicName, kafkaMessageKey, event);
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
