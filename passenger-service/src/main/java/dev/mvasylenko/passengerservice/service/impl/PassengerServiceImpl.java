package dev.mvasylenko.passengerservice.service.impl;

import dev.mvasylenko.core.dto.PassengerDto;
import dev.mvasylenko.passengerservice.entity.Passenger;
import dev.mvasylenko.passengerservice.mapper.PassengerMapper;
import dev.mvasylenko.passengerservice.repository.PassengerRepository;
import dev.mvasylenko.passengerservice.service.PassengerService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;

    public PassengerServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public PassengerDto findByEmail(String email) {
        return PassengerMapper.INSTANCE.passengerToPassengerDto(passengerRepository.findByEmail(email));
    }

    @Override
    @Transactional
    public PassengerDto createNewPassenger(String email, String name) {
        Passenger passenger = new Passenger(email, name);
        passengerRepository.save(passenger);

        return PassengerMapper.INSTANCE.passengerToPassengerDto(passenger);
    }
}
