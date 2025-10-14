package dev.mvasylenko.carservice.service;

import dev.mvasylenko.core.dto.CarRentalHistoryDto;
import dev.mvasylenko.core.enums.CarRentalStatus;

import java.util.List;
import java.util.UUID;

public interface CarRentalHistoryService {
    void add(UUID carId, UUID driverId, CarRentalStatus status);

    List<CarRentalHistoryDto> getCarRentalHistory(UUID carId);

}
