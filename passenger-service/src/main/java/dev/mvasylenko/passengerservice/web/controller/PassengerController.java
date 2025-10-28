package dev.mvasylenko.passengerservice.web.controller;

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
    public Map<String, String> createNewPassenger(Map<String, String> attributes) {
        var email = attributes.get(EMAIL);
        var name = attributes.get(NAME);

        var existedPassenger = passengerService.findByEmail(email);
        if (existedPassenger != null) {
            throw new PassengerAlreadyExistExeption("Passenger with email " + email + " already exists!");
        }

        var passenger = passengerService.createNewPassenger(email, name);

        Map<String, String> result = new HashMap<>();
        result.put("id", passenger.getId().toString());

        return result;
    }
}
