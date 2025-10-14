package dev.mvasylenko.core.dto;

import dev.mvasylenko.core.enums.PaymentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentDto {
    private UUID id;

    @DecimalMin(value = "0.00")
    @NotNull
    private BigDecimal amount;

    @NotNull
    private UUID senderId;

    @NotNull
    private UUID receiverId;

    @NotNull
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @NotNull
    private PaymentStatus status;

    public PaymentDto() {
    }

    public PaymentDto(UUID id, BigDecimal amount, UUID senderId, UUID receiverId, LocalDateTime date, PaymentStatus status) {
        this.id = id;
        this.amount = amount;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.date = date;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }

    public UUID getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(UUID receiverId) {
        this.receiverId = receiverId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
