package dev.mvasylenko.core.commands;

import java.util.UUID;

public class CommitPaymentCommand {
    private UUID paymentId;

    public CommitPaymentCommand() {
    }

    public CommitPaymentCommand(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }
}
