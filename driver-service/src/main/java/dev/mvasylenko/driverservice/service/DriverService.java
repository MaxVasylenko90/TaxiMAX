package dev.mvasylenko.driverservice.service;

import dev.mvasylenko.core.commands.WithdrawDriverAmountCommand;
import dev.mvasylenko.core.dto.CarDto;
import dev.mvasylenko.core.dto.RentCarRequest;

import java.math.BigDecimal;
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
}
