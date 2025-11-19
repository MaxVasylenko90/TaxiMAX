package dev.mvasylenko.carservice.service;

import dev.mvasylenko.carservice.exception.CarAlreadyReservedException;
import dev.mvasylenko.carservice.exception.CarNotFoundException;
import dev.mvasylenko.carservice.exception.NotAvailableCarsException;
import dev.mvasylenko.core.dto.CarDto;
import dev.mvasylenko.core.dto.RentCarRequest;
import dev.mvasylenko.core.enums.CarRentalStatus;

import java.util.Set;
import java.util.UUID;

public interface CarService {
    /**
     * Reserve a car for driver and throws CarAlreadyReservedException if car already reserved
     * or CarNotFoundException in case the car wasn't found
     * or IllegalArgumentException if carId or driverId is null
     * @param carId
     * @param rentCarRequest
     * @throws CarAlreadyReservedException
     * @throws CarNotFoundException
     * @throws IllegalArgumentException
     */
    void reserveCarForDriver(UUID carId, RentCarRequest rentCarRequest) throws CarAlreadyReservedException,
            CarNotFoundException, IllegalArgumentException;

    /**
     * Method to get all available cars which drivers can rent at the moment
     * @return Set of CarDto objects
     * @throws NotAvailableCarsException
     */
    Set<CarDto> getAvailableCarsForRent() throws NotAvailableCarsException;

    /**
     * Registration of a new car in taxiMax company
     * @param carDto - new car to register
     * @return - registered carDto
     */
    CarDto registerNewCar(CarDto carDto);

    /**
     * Get car by car id
     * @param carId
     * @return CarDto object
     */
    CarDto getCar(UUID carId);

    /**
     * Change car rental status
     * @param status
     * @param kafkaMessageKey
     */
    void changeCarRentalStatus(UUID carId, CarRentalStatus status, String kafkaMessageKey);

    /**
     * Release car
     * @param carId
     * @param status
     * @param kafkaMessageKey
     * @param senderId
     */
    void releaseCar(UUID carId, CarRentalStatus status, String kafkaMessageKey, UUID senderId);
}
