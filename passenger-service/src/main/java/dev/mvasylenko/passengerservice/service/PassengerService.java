package dev.mvasylenko.passengerservice.service;

import dev.mvasylenko.core.events.PassengerRegisteredEvent;
import org.springframework.kafka.support.Acknowledgment;

public interface PassengerService {
    /**
     * Create passenger
     * @param event
     * @param kafkaMessageKey
     * @param acknowledgment
     */
    void createPassenger(PassengerRegisteredEvent event, String kafkaMessageKey, Acknowledgment acknowledgment);
}
