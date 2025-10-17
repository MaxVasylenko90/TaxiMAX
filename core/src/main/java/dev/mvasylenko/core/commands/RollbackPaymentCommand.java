package dev.mvasylenko.core.commands;

import java.util.UUID;

public class RollbackPaymentCommand {
    private UUID commandId = UUID.randomUUID();
    private UUID paymentId;
    private String reason;
    private UUID senderId;

    public RollbackPaymentCommand() {
    }

    public RollbackPaymentCommand(UUID paymentId, String reason, UUID senderId) {
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

    public UUID getCommandId() {
        return commandId;
    }

    public void setCommandId(UUID commandId) {
        this.commandId = commandId;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }
}
