package dev.mvasylenko.core.events;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentCreatedEvent {
    private UUID eventId = UUID.randomUUID();
    private UUID paymentId;
    private UUID senderId;
    private BigDecimal carRentPrice;

    public PaymentCreatedEvent() {
    }

    public PaymentCreatedEvent(UUID paymentId, UUID senderId, BigDecimal carRentPrice) {
        this.paymentId = paymentId;
        this.senderId = senderId;
        this.carRentPrice = carRentPrice;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
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
