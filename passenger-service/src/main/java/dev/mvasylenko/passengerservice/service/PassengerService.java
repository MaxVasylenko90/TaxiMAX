package dev.mvasylenko.passengerservice.service;

import dev.mvasylenko.core.dto.PassengerDto;
import dev.mvasylenko.passengerservice.entity.Passenger;

public interface PassengerService {
    PassengerDto findByEmail(String email);

    PassengerDto createNewPassenger(String email, String name);
}
