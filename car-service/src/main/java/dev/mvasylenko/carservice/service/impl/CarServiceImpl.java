package dev.mvasylenko.carservice.service.impl;

import dev.mvasylenko.carservice.entity.Car;
import dev.mvasylenko.carservice.exception.CarAlreadyReservedException;
import dev.mvasylenko.carservice.exception.CarNotFoundException;
import dev.mvasylenko.carservice.exception.NotAvailableCarsException;
import dev.mvasylenko.carservice.mapper.CarMapper;
import dev.mvasylenko.carservice.repository.CarRepository;
import dev.mvasylenko.carservice.service.CarRentalHistoryService;
import dev.mvasylenko.carservice.service.CarService;
import dev.mvasylenko.core.commands.AssignCarToDriverCommand;
import dev.mvasylenko.core.dto.CarDto;
import dev.mvasylenko.core.dto.RentCarRequest;
import dev.mvasylenko.core.enums.CarRentalStatus;
import dev.mvasylenko.core.events.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private static final Logger LOG = LoggerFactory.getLogger(CarServiceImpl.class);
    private static final int ZERO_UPDATED_CARS = 0;

    private final CarRepository carRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String carEventsTopicName;
    private final String driverCommandsTopic;
    private final CarRentalHistoryService carRentalHistoryService;

    public CarServiceImpl(CarRepository carRepository, KafkaTemplate<String, Object> kafkaTemplate,
                          @Value("${car.events.topic.name}") String carEventsTopicName,
                          @Value("${driver.commands.topic.name}") String driverCommandsTopic,
                          CarRentalHistoryService carRentalHistoryService) {
        this.carRepository = carRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.carEventsTopicName = carEventsTopicName;
        this.driverCommandsTopic = driverCommandsTopic;
        this.carRentalHistoryService = carRentalHistoryService;
    }

    @Override
    @Transactional
    public void reserveCarForDriver(UUID carId, RentCarRequest request) throws CarAlreadyReservedException,
            CarNotFoundException, IllegalArgumentException {
        validateIds(carId, request);

        var driverId = request.getDriverId();

        var car = getCarById(carId);

        var reservationResult = carRepository.reserveCarForDriver(car.getId(), driverId);

        if (reservationResult == ZERO_UPDATED_CARS) {
            throw new CarAlreadyReservedException("Car with id=" + carId + " already reserved!");
        }

        carRentalHistoryService.add(carId, driverId, CarRentalStatus.RESERVED);

        CarSuccessfullyReservedEvent event = new CarSuccessfullyReservedEvent(carId, driverId, car.getRentPrice());
        kafkaTemplate.send(carEventsTopicName, car.getId().toString(), event);

        LOG.info("Car with id={} was reserved for driver {}. Waiting for payment.", carId, driverId);
    }

    @Override
    public Set<CarDto> getAvailableCarsForRent() throws NotAvailableCarsException {
        var cars = carRepository.findAllByAvailable(Boolean.TRUE);

        if (cars.isEmpty()) {
            throw new NotAvailableCarsException("All cars are occupied. There are no cars available at the moment.");
        }

        return cars.stream()
                .map(CarMapper.INSTANCE::carToCarDto)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public CarDto registerNewCar(CarDto carDto) {
        Car car = CarMapper.INSTANCE.carDtoToCar(carDto);
        carRepository.save(car);
        return CarMapper.INSTANCE.carToCarDto(car);
    }

    @Override
    public CarDto getCar(UUID carId) {
        return CarMapper.INSTANCE.carToCarDto(getCarById(carId));
    }

    @Override
    public void changeCarRentalStatus(UUID carId, CarRentalStatus status, String kafkaMessageKey) {
        var driverId = getCarById(carId).getDriverId();
        carRentalHistoryService.add(carId, driverId, status);

        if (status.equals(CarRentalStatus.APPROVED)) {
            AssignCarToDriverCommand command = new AssignCarToDriverCommand(carId, driverId);
            kafkaTemplate.send(driverCommandsTopic, kafkaMessageKey, command);
        }
    }

    @Override
    public void changeCarRentalStatus(UUID carId, CarRentalStatus status, String kafkaMessageKey, UUID senderId) {
        carRentalHistoryService.add(carId, senderId, status);
    }

    @Override
    @Transactional
    public void releaseCar(UUID carId) {
        var car = getCarById(carId);
        car.setAvailable(Boolean.TRUE);
        car.setDriverId(null);
        carRepository.save(car);

        LOG.info("Car with id={} has been released and is again available for rental.", carId);
    }

    private void validateIds(UUID carId, RentCarRequest request) {
        if (carId == null || request == null) {
            throw new IllegalArgumentException("CarId and driver cannot be null");
        }
    }

    private Car getCarById(UUID carId) {
        return carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException("Car with id=" + carId + " wasn't found!"));
    }
}
