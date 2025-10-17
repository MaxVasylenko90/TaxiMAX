package dev.mvasylenko.core.events;

import java.math.BigDecimal;
import java.util.UUID;

public class CarSuccessfullyReservedEvent {
    private UUID eventId = UUID.randomUUID();
    private UUID carId;
    private UUID driverId;
    private BigDecimal carRentPrice;

    public CarSuccessfullyReservedEvent() {
    }

    public CarSuccessfullyReservedEvent(UUID carId, UUID driverId, BigDecimal carRentPrice) {
        this.carId = carId;
        this.driverId = driverId;
        this.carRentPrice = carRentPrice;
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

    public BigDecimal getCarRentPrice() {
        return carRentPrice;
    }

    public void setCarRentPrice(BigDecimal carRentPrice) {
        this.carRentPrice = carRentPrice;
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }
}
