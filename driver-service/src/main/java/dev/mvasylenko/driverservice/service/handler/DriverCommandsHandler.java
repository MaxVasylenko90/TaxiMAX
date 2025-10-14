package dev.mvasylenko.driverservice.service.handler;

import dev.mvasylenko.core.commands.WithdrawDriverAmountCommand;
import dev.mvasylenko.core.dto.PaymentDto;
import dev.mvasylenko.core.events.PaymentCreatedEvent;
import dev.mvasylenko.driverservice.service.DriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics={"${driver.commands.topic.name}"})
public class DriverCommandsHandler {
    private final Logger LOG = LoggerFactory.getLogger(DriverCommandsHandler.class);
    private final DriverService driverService;

    public DriverCommandsHandler(DriverService driverService) {
        this.driverService = driverService;
    }

    @KafkaHandler
    public void handleCommand(@Payload WithdrawDriverAmountCommand command,
                              @Header(KafkaHeaders.RECEIVED_KEY) String kafkaMessageKey) {
        driverService.withdraw(command, kafkaMessageKey);
    }
}
