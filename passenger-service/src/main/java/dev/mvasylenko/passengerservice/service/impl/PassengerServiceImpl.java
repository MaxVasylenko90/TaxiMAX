package dev.mvasylenko.passengerservice.service.impl;

import dev.mvasylenko.core.dto.PassengerDto;
import dev.mvasylenko.core.dto.PassengerRegistrationDto;
import dev.mvasylenko.passengerservice.entity.Passenger;
import dev.mvasylenko.passengerservice.exception.PassengerAlreadyExistExeption;
import dev.mvasylenko.passengerservice.mapper.PassengerMapper;
import dev.mvasylenko.passengerservice.repository.PassengerRepository;
import dev.mvasylenko.passengerservice.service.PassengerService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;

    public PassengerServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    @Transactional
    public PassengerDto createNewPassenger(String email, String name) {
        checkExistedPassengerByEmail(email);
        Passenger passenger = new Passenger(email, name);
        passengerRepository.save(passenger);

        return PassengerMapper.INSTANCE.passengerToPassengerDto(passenger);
    }

    @Override
    public PassengerDto createNewPassenger(PassengerRegistrationDto passengerDto) {
        var email = passengerDto.getEmail();
        checkExistedPassengerByEmail(email);

        Passenger passenger = new Passenger(
                passengerDto.getName(),
                passengerDto.getSurname(),
                passengerDto.getPassword(),
                email,
                passengerDto.getPhone(),
                BigDecimal.ZERO);

        return PassengerMapper.INSTANCE.passengerToPassengerDto(passenger);
    }

    private void checkExistedPassengerByEmail(String email) {
        if (passengerRepository.findByEmail(email) != null) {
            throw new PassengerAlreadyExistExeption("Passenger with email " + email + " already exists!");
        }
    }
}
