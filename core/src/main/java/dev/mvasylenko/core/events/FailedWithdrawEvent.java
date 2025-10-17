package dev.mvasylenko.core.events;

import java.util.UUID;

public class FailedWithdrawEvent {
    private UUID eventId = UUID.randomUUID();
    private UUID paymentId;
    private String reason;
    private UUID senderId;

    public FailedWithdrawEvent() {
    }

    public FailedWithdrawEvent(UUID paymentId, String reason, UUID senderId) {
        this.paymentId = paymentId;
        this.reason = reason;
        this.senderId = senderId;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
