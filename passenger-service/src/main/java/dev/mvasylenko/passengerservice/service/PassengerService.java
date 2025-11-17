package dev.mvasylenko.passengerservice.service;

import dev.mvasylenko.core.events.UserRegisteredEvent;
import org.springframework.kafka.support.Acknowledgment;

public interface PassengerService {
    void createPassenger(UserRegisteredEvent event, String kafkaMessageKey, Acknowledgment acknowledgment);

}
