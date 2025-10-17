package dev.mvasylenko.core.events;

import java.util.UUID;

public class PaymentCancelledEvent {
    private UUID eventId = UUID.randomUUID();
    private UUID carId;
    private UUID senderId;

    public PaymentCancelledEvent() {
    }

    public PaymentCancelledEvent(UUID carId, UUID senderId) {
        this.carId = carId;
        this.senderId = senderId;
    }

    public UUID getCarId() {
        return carId;
    }

    public void setCarId(UUID carId) {
        this.carId = carId;
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }
}
