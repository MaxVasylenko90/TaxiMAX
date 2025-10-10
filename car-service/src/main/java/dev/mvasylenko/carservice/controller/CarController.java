package dev.mvasylenko.carservice.controller;

import dev.mvasylenko.carservice.exception.CarNotFoundException;
import dev.mvasylenko.carservice.service.CarService;
import dev.mvasylenko.core.dto.CarDto;
import dev.mvasylenko.core.dto.RentCarRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/cars")
public class CarController {
    private static final String MESSAGE = "message";

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/available")
    @ResponseStatus(HttpStatus.OK)
    public Set<CarDto> getAvailableCarsForRent() throws CarNotFoundException {
        return carService.getAvailableCarsForRent();
    }

    @PatchMapping("/{carId}/rent")
    public ResponseEntity<Map<String, String>> rentCar(@PathVariable UUID carId,
                                                       @RequestBody @Valid RentCarRequest request) {
        carService.reserveCarForDriver(carId, request.getDriverId());
        return ResponseEntity.accepted().body(Collections.singletonMap(MESSAGE, "The car was successfully rent!"));
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.OK)
    public CarDto registerNewCar(@RequestBody @Valid CarDto carDto) {
        return carService.registerNewCar(carDto);
    }
}
