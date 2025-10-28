package dev.mvasylenko.driverservice.web.controller;

import dev.mvasylenko.core.dto.CarDto;
import dev.mvasylenko.core.dto.DriverDto;
import dev.mvasylenko.core.dto.RentCarRequest;
import dev.mvasylenko.driverservice.service.DriverService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static dev.mvasylenko.core.constants.CoreConstants.MESSAGE;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public ResponseEntity<List<DriverDto>> getAllDrivers() {
        return ResponseEntity.ok(driverService.getAllDrivers());
    }

    @GetMapping("/available-cars")
    public ResponseEntity<Set<CarDto>> getAvailableCars() {
        return ResponseEntity.ok(driverService.getAvailableToRentCars());
    }

    @PostMapping("/{driverId}/rentCar")
    public ResponseEntity<Map<String, String>> rentCar(@RequestBody RentCarRequest rentCarRequest) {
        return ResponseEntity.accepted().body(driverService.rentCarForDriver(rentCarRequest));
    }

    @PatchMapping("/{driverId}/startShift")
    public ResponseEntity<Map<String, String>> startShift(@PathVariable UUID driverId) {
        driverService.startShift(driverId);
        return ResponseEntity.accepted().body(Collections.singletonMap(MESSAGE, "The driver is ready to work!"));
    }

    @GetMapping("/{driverId}")
    public ResponseEntity<DriverDto> getDriver(@PathVariable UUID driverId) {
        return ResponseEntity.ok().body(driverService.findDriver(driverId));
    }
}
