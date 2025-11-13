package dev.mvasylenko.passengerservice.web.controller;

import dev.mvasylenko.core.dto.PassengerDto;
import dev.mvasylenko.core.dto.PassengerRegistrationDto;
import dev.mvasylenko.passengerservice.exception.PassengerAlreadyExistExeption;
import dev.mvasylenko.passengerservice.service.PassengerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static dev.mvasylenko.core.constants.CoreConstants.*;

@RestController("/passengers")
public class PassengerController {
    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping("/create")
    public PassengerDto createNewPassenger(Map<String, String> attributes) {
        var email = attributes.get(EMAIL);
        var name = attributes.get(NAME);

       return passengerService.createNewPassenger(email, name);
    }

    @PostMapping(value = "/create", consumes = "application/json")
    public PassengerDto createNewPassenger(PassengerRegistrationDto registrationDto) {
        return passengerService.createNewPassenger(registrationDto);
    }
}
