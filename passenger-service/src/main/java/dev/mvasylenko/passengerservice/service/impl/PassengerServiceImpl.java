package dev.mvasylenko.passengerservice.service.impl;

import dev.mvasylenko.core.enums.Role;
import dev.mvasylenko.core.events.PassengerRegisteredEvent;
import dev.mvasylenko.passengerservice.entity.Passenger;
import dev.mvasylenko.passengerservice.repository.PassengerRepository;
import dev.mvasylenko.passengerservice.service.PassengerService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Service
@KafkaListener(topics = {"${registration.events.topic.name}"})
public class PassengerServiceImpl implements PassengerService {
    private static final Logger LOG = LoggerFactory.getLogger(PassengerServiceImpl.class);
    private static final long TTL_HOURS = 24;
    private final PassengerRepository passengerRepository;
    private final RedisTemplate<String, Object> redisTemplate;


    public PassengerServiceImpl(PassengerRepository passengerRepository, RedisTemplate<String, Object> redisTemplate) {
        this.passengerRepository = passengerRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    @KafkaHandler
    @Transactional
    public void createPassenger(@Payload PassengerRegisteredEvent event,
                                @Header(KafkaHeaders.RECEIVED_KEY) String kafkaMessageKey,
                                Acknowledgment acknowledgment) {
        if (event.getRole() != Role.PASSENGER) {
            acknowledgment.acknowledge();
            return;
        }
        var eventId = event.getEventId().toString();
        if (redisTemplate.hasKey(eventId)) {
            LOG.info("Event with id={} has already processed! Skipping...", eventId);
            acknowledgment.acknowledge();
            return;
        }
        try {
            passengerRepository.save(createPassengerFromEvent(event));
            redisTemplate.opsForValue().set(eventId, true, TTL_HOURS, TimeUnit.HOURS);
            acknowledgment.acknowledge();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    private  Passenger createPassengerFromEvent(PassengerRegisteredEvent event) {
        var passenger = new Passenger();

        passenger.setId(event.getUserId());
        passenger.setName(event.getName());
        passenger.setSurname(event.getSurname());
        passenger.setEmail(event.getEmail());
        passenger.setPhone(event.getPhone());
        passenger.setRole(event.getRole());
        if (isAllPassengerRelatedParamsNotNull(event)) {
            passenger.setAccountCompleted(true);
        }

        return passenger;
    }

    private boolean isAllPassengerRelatedParamsNotNull(PassengerRegisteredEvent event) {
        return Stream.of(event.getUserId(), event.getName(), event.getSurname(),
                        event.getEmail(), event.getPhone(), event.getRole())
                .allMatch(Objects::nonNull);
    }
}
