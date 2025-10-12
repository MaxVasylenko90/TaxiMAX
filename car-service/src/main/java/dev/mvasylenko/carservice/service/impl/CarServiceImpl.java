package dev.mvasylenko.carservice.service.impl;

import dev.mvasylenko.carservice.entity.Car;
import dev.mvasylenko.carservice.exception.CarAlreadyReservedException;
import dev.mvasylenko.carservice.exception.CarNotFoundException;
import dev.mvasylenko.carservice.exception.NotAvailableCarsException;
import dev.mvasylenko.carservice.mapper.CarMapper;
import dev.mvasylenko.carservice.repository.CarRepository;
import dev.mvasylenko.carservice.service.CarService;
import dev.mvasylenko.core.dto.CarDto;
import dev.mvasylenko.core.event.CarSuccessfullyReservedEvent;
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

    public CarServiceImpl(CarRepository carRepository, KafkaTemplate<String, Object> kafkaTemplate,
                          @Value("${car.events.topic.name}") String carEventsTopicName) {
        this.carRepository = carRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.carEventsTopicName = carEventsTopicName;
    }

    @Override
    public void reserveCarForDriver(UUID carId, UUID driverId) throws CarAlreadyReservedException,
            CarNotFoundException, IllegalArgumentException {
        validateIds(carId, driverId);

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException("Car with id=" + carId +" already reserved"));

        var reservationResult = carRepository.reserveCarForDriver(car.getId(), driverId);

        if (reservationResult == ZERO_UPDATED_CARS) {
            throw new CarAlreadyReservedException("Car with id=" + carId +" already reserved!");
        }

        CarSuccessfullyReservedEvent event = new CarSuccessfullyReservedEvent(carId, driverId);
        kafkaTemplate.send(carEventsTopicName, car.getId().toString(), event);

        LOG.info("Car with id={} was reserved for driver {}", carId, driverId);
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

    private void validateIds(UUID carId, UUID driverId) {
        if (carId == null || driverId == null) {
            throw new IllegalArgumentException("CarId and driverId cannot be null");
        }
    }
}
