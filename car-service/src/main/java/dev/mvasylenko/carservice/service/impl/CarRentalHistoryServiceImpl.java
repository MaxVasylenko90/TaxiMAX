package dev.mvasylenko.carservice.service.impl;

import dev.mvasylenko.carservice.entity.CarRentalHistory;
import dev.mvasylenko.carservice.mapper.CarRentalHistoryMapper;
import dev.mvasylenko.carservice.repository.CarRentalHistoryRepository;
import dev.mvasylenko.carservice.service.CarRentalHistoryService;
import dev.mvasylenko.core.dto.CarRentalHistoryDto;
import dev.mvasylenko.core.enums.CarRentalStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CarRentalHistoryServiceImpl implements CarRentalHistoryService {
    private final CarRentalHistoryRepository repository;

    public CarRentalHistoryServiceImpl(CarRentalHistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void add(UUID carId, UUID driverId, CarRentalStatus status) {
        CarRentalHistory history = new CarRentalHistory(carId, driverId, status, LocalDateTime.now());
        repository.save(history);
    }

    @Override
    public List<CarRentalHistoryDto> getCarRentalHistory(UUID carId) {
        var histories = repository.findByCarId(carId);
        return histories.stream()
                .map(CarRentalHistoryMapper.INSTANCE::historyToHistoryDto)
                .toList();
    }
}
