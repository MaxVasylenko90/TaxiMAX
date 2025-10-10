package dev.mvasylenko.core.event;

import java.util.UUID;

public class CarSuccessfullyReservedEvent {
    private UUID carId;
    private UUID driverId;

    public CarSuccessfullyReservedEvent() {
    }

    public CarSuccessfullyReservedEvent(UUID carId, UUID driverId) {
        this.carId = carId;
        this.driverId = driverId;
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
}
