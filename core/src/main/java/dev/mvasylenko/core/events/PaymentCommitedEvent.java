package dev.mvasylenko.core.events;

import java.util.UUID;

public class PaymentCommitedEvent {
    private UUID carId;

    public PaymentCommitedEvent() {
    }

    public PaymentCommitedEvent(UUID carId) {
        this.carId = carId;
    }

    public UUID getCarId() {
        return carId;
    }

    public void setCarId(UUID carId) {
        this.carId = carId;
    }
}
