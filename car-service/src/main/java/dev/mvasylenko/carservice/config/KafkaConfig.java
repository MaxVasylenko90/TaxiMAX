package dev.mvasylenko.carservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfig {
    private final static Integer TOPIC_REPLICATION_FACTOR = 3;
    private final static Integer TOPIC_PARTITIONS = 3;

    private final String carEventsTopicName;
    private final String paymentCommandsTopicName;
    private final String driverCommandsTopicName;
    private final String carCommandsTopicName;

    public KafkaConfig( @Value("${car.events.topic.name}") String carEventsTopicName,
                        @Value("${payment.commands.topic.name}") String paymentCommandsTopicName,
                        @Value("${driver.commands.topic.name}") String driverCommandsTopicName,
                        @Value("${car.commands.topic.name}") String carCommandsTopicName) {
        this.carEventsTopicName = carEventsTopicName;
        this.paymentCommandsTopicName = paymentCommandsTopicName;
        this.driverCommandsTopicName = driverCommandsTopicName;
        this.carCommandsTopicName = carCommandsTopicName;
    }

    @Bean
    KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    NewTopic createCarEventsTopic() {
        return TopicBuilder.name(carEventsTopicName)
                .partitions(TOPIC_PARTITIONS)
                .replicas(TOPIC_REPLICATION_FACTOR)
                .build();
    }

    @Bean
    NewTopic createPaymentCommandsTopic() {
        return TopicBuilder.name(paymentCommandsTopicName)
                .partitions(TOPIC_PARTITIONS)
                .replicas(TOPIC_REPLICATION_FACTOR)
                .build();
    }

    @Bean
    NewTopic createDriverCommandsTopic() {
        return TopicBuilder.name(driverCommandsTopicName)
                .partitions(TOPIC_PARTITIONS)
                .replicas(TOPIC_REPLICATION_FACTOR)
                .build();
    }

    @Bean
    NewTopic createCarCommandsTopic() {
        return TopicBuilder.name(carCommandsTopicName)
                .partitions(TOPIC_PARTITIONS)
                .replicas(TOPIC_REPLICATION_FACTOR)
                .build();
    }
}
