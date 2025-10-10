package dev.mvasylenko.core.dto;

import java.util.UUID;

public class RentCarRequest {
    private UUID driverId;

    public RentCarRequest() {
    }

    public RentCarRequest(UUID driverId) {
        this.driverId = driverId;
    }

    public UUID getDriverId() {
        return driverId;
    }

    public void setDriverId(UUID driverId) {
        this.driverId = driverId;
    }
}
