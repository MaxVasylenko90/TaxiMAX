package dev.mvasylenko.core.dto;

import dev.mvasylenko.core.enums.CarRentalStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public class CarRentalHistoryDto {

    private UUID id;

    @NotNull
    private UUID carId;

    @NotNull
    private UUID driverId;

    @NotNull
    private CarRentalStatus status;

    @NotNull
    private LocalDateTime date;

    public CarRentalHistoryDto() {
    }

    public CarRentalHistoryDto(UUID id, UUID carId, UUID driverId, CarRentalStatus status, LocalDateTime date) {
        this.id = id;
        this.carId = carId;
        this.driverId = driverId;
        this.status = status;
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCarId() {
        return carId;
    }

    public void setCarId(UUID carId) {
        this.carId = carId;
    }

    public UUID getDriverId() {
        return driverId;
    }

    public void setDriverId(UUID driverId) {
        this.driverId = driverId;
    }

    public CarRentalStatus getStatus() {
        return status;
    }

    public void setStatus(CarRentalStatus status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
