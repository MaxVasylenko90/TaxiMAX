package dev.mvasylenko.core.events;

import java.util.UUID;

public class SuccessfulWithdrawEvent {
    private UUID eventId = UUID.randomUUID();
    private UUID paymentId;

    public SuccessfulWithdrawEvent() {
    }

    public SuccessfulWithdrawEvent(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }
}
