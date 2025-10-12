package dev.mvasylenko.driverservice.saga;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics={
        "${orders.events.topic.name}",
        "${products.events.topic.name}",
        "${payments.events.topic.name}"})
public class DriverSaga {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public DriverSaga(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}
