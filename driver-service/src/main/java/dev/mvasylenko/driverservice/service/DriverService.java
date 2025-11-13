package dev.mvasylenko.driverservice.service;

import dev.mvasylenko.core.commands.WithdrawDriverAmountCommand;
import dev.mvasylenko.core.dto.CarDto;
import dev.mvasylenko.core.dto.DriverDto;
import dev.mvasylenko.core.dto.RentCarRequest;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface DriverService {
    /**
     * Get available cars for rent
     * @return set of available cars
     */
    Set<CarDto> getAvailableToRentCars();

    /**
     * REST request to rent car for driver
     *
     * @param rentCarRequest
     * @return
     */
    Map<String, String> rentCarForDriver(RentCarRequest rentCarRequest);

    /**
     * Change the driver status to ONLINE so he can work
     * @param driverId
     */
    void startShift(UUID driverId);

    /**
     * Withdraw driver amount
     * @param command
     * @param kafkaMessageKey
     */
    void withdraw(WithdrawDriverAmountCommand command, String kafkaMessageKey);

    /**
     * Get list of all drivers
     * @return  List<DriverDto>
     */
    List<DriverDto> getAllDrivers();

    /**
     * Assign car for driver
     * @param carId
     * @param driverId
     */
    void assignCarForDriver(UUID carId, UUID driverId);

    /**
     * Find driver by id
     * @param driverId
     * @return DriverDto object
     */
    DriverDto findDriver(UUID driverId);
}
