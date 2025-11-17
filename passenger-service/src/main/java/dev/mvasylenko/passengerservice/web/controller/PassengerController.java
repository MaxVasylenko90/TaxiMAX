package dev.mvasylenko.passengerservice.web.controller;

import dev.mvasylenko.passengerservice.service.PassengerService;
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


}
