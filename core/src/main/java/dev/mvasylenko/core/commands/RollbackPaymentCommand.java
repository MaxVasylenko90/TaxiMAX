package dev.mvasylenko.core.commands;

import java.util.UUID;

public class RollbackPaymentCommand {
    private UUID paymentId;
    private String reason;

    public RollbackPaymentCommand() {
    }

    public RollbackPaymentCommand(UUID paymentId, String reason) {
        this.paymentId = paymentId;
        this.reason = reason;
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
}
