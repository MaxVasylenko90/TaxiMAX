package dev.mvasylenko.carservice.controller;

import dev.mvasylenko.carservice.exception.CarNotFoundException;
import dev.mvasylenko.carservice.service.CarRentalHistoryService;
import dev.mvasylenko.carservice.service.CarService;
import dev.mvasylenko.core.dto.CarDto;
import dev.mvasylenko.core.dto.CarRentalHistoryDto;
import dev.mvasylenko.core.dto.RentCarRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static dev.mvasylenko.core.constants.CoreConstants.MESSAGE;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;
    private final CarRentalHistoryService carRentalHistoryService;

    public CarController(CarService carService, CarRentalHistoryService carRentalHistoryService) {
        this.carService = carService;
        this.carRentalHistoryService = carRentalHistoryService;
    }

    @GetMapping("/available")
    @ResponseStatus(HttpStatus.OK)
    public Set<CarDto> getAvailableCarsForRent() throws CarNotFoundException {
        return carService.getAvailableCarsForRent();
    }

    @PatchMapping("/{carId}/rent")
    public ResponseEntity<Map<String, String>> rentCar(@PathVariable UUID carId,
                                                       @RequestBody @Valid RentCarRequest request) {
        carService.reserveCarForDriver(carId, request);
        return ResponseEntity.accepted().body(
                Map.of("status", "PENDING_PAYMENT", MESSAGE, "Reservation in progress"));
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.OK)
    public CarDto registerNewCar(@RequestBody @Valid CarDto carDto) {
        return carService.registerNewCar(carDto);
    }

    @GetMapping("/{carId}/history")
    public List<CarRentalHistoryDto> getCarRentalHistory(UUID carId) {
        return carRentalHistoryService.getCarRentalHistory(carId);
    }

    @GetMapping("/{carId}")
    public CarDto getCar(UUID carId) {
        return carService.getCar(carId);
    }
}
