package dev.mvasylenko.core.events;

import java.util.UUID;

public class PaymentCancelledEvent {
    private UUID carId;

    public PaymentCancelledEvent() {
    }

    public PaymentCancelledEvent(UUID carId) {
        this.carId = carId;
    }

    public UUID getCarId() {
        return carId;
    }

    public void setCarId(UUID carId) {
        this.carId = carId;
    }
}
