package dev.mvasylenko.core.dto;

import java.util.UUID;

public class RentCarRequest {

    private UUID carId;

    private UUID driverId;

    public RentCarRequest() {
    }

    public RentCarRequest(UUID carId, UUID driverId) {
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
