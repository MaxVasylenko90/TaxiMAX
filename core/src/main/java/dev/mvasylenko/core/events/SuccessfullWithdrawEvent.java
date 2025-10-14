package dev.mvasylenko.core.events;

import java.util.UUID;

public class SuccessfullWithdrawEvent {
    private UUID paymentId;

    public SuccessfullWithdrawEvent() {
    }

    public SuccessfullWithdrawEvent(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }
}
