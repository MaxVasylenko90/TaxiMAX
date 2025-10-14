package dev.mvasylenko.driverservice.service.impl;

import dev.mvasylenko.core.commands.WithdrawDriverAmountCommand;
import dev.mvasylenko.core.dto.CarDto;
import dev.mvasylenko.core.dto.RentCarRequest;
import dev.mvasylenko.core.enums.DriverStatus;
import dev.mvasylenko.core.events.FailedWithdrawEvent;
import dev.mvasylenko.core.events.SuccessfullWithdrawEvent;
import dev.mvasylenko.driverservice.entity.Driver;
import dev.mvasylenko.driverservice.exception.DriverHasNoCarException;
import dev.mvasylenko.driverservice.exception.DriverNotFoundException;
import dev.mvasylenko.driverservice.repository.DriverRepository;
import dev.mvasylenko.driverservice.service.DriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static dev.mvasylenko.core.constants.CoreConstants.CAR_SERVICE_URL;

@Service
public class DriverServiceImpl implements DriverService {
    private static final Logger LOG = LoggerFactory.getLogger(DriverServiceImpl.class);
    public static final String NOT_ENOUGH_MONEY_TO_WITHDRAW = "Not enough money to withdraw!";

    private final DriverRepository driverRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RestTemplate restTemplate;
    private final String availableCarsUrl;
    private final String driverEventsTopicName;

    public DriverServiceImpl(DriverRepository driverRepository,
                             KafkaTemplate<String, Object> kafkaTemplate,
                             RestTemplate restTemplate,
                             @Value("${available.cars.url}") String availableCarsUrl,
                             @Value("${driver.events.topic.name}") String driverEventsTopicName) {
        this.driverRepository = driverRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.restTemplate = restTemplate;
        this.availableCarsUrl = availableCarsUrl;
        this.driverEventsTopicName = driverEventsTopicName;
    }

    @Override
    public Set<CarDto> getAvailableToRentCars() {
        ResponseEntity<Set<CarDto>> response =
                restTemplate.exchange(availableCarsUrl, HttpMethod.GET,
                        null, new ParameterizedTypeReference<>() {
                        });
        return response.getBody();
    }

//    @Override
//    public void rentCarForDriver(RentCarRequest rentCarRequest, UUID driverId) {
//        var driver = driverRepository.findById(driverId)
//                .orElseThrow(() -> new DriverNotFoundException("Driver with id=" + driverId + " wasn't found!"));
//        RentCarCommand command = new RentCarCommand(rentCarRequest.getCarId(), DriverMapper.INSTANCE.driverToDto(driver));
//        kafkaTemplate.send(carCommandsTopicName, driverId.toString(), command);
//    }

    @Override
    public Map<String, String> rentCarForDriver(RentCarRequest rentCarRequest) {

        final String rentUrl = CAR_SERVICE_URL + "/" + rentCarRequest.getCarId() + "/rent";

        HttpEntity<RentCarRequest> entity = new HttpEntity<>(rentCarRequest);

        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                rentUrl, HttpMethod.PATCH, entity, new ParameterizedTypeReference<>() {
                });

        return response.getBody();
    }

    @Override
    @Transactional
    public void startShift(UUID driverId) {
        var driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException("Driver with id=" + driverId + " wasn't found!"));

        if (driver.getCarId() == null) {
            throw new DriverHasNoCarException("Driver with id=" + driverId + " has no car assigned!");
        }

        driver.setStatus(DriverStatus.ONLINE);
        driverRepository.save(driver);

//        DriverReadyForWorkEvent event = new DriverReadyForWorkEvent(driverId, driver.getCarId());
//        kafkaTemplate.send(driverEventsTopicName, driverId.toString(), event);
    }

    @Override
    @Transactional
    public void withdraw(WithdrawDriverAmountCommand command, String kafkaMessageKey) {
        var driverId = command.getDriverId();
        var driver = driverRepository.findByIdForUpdate(driverId)
                .orElseThrow(() -> new DriverNotFoundException("Driver with id=" + driverId + " wasn't found!"));

        if (driver.getAmount().compareTo(command.getAmount()) < 0) {
            LOG.error("Driver with id={} doesn't have enough money to rent car!", driverId);
            FailedWithdrawEvent event = new FailedWithdrawEvent(command.getPaymentId(), NOT_ENOUGH_MONEY_TO_WITHDRAW);
            kafkaTemplate.send(driverEventsTopicName, kafkaMessageKey, event);
        }

        driver.setAmount(driver.getAmount().subtract(command.getAmount()));

        driverRepository.save(driver);

        SuccessfullWithdrawEvent event = new SuccessfullWithdrawEvent(command.getPaymentId());
        kafkaTemplate.send(driverEventsTopicName, kafkaMessageKey, event);
    }
}
