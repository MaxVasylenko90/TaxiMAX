package dev.mvasylenko.core.dto;

import dev.mvasylenko.core.enums.PaymentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentHistoryDto {
    private UUID id;

    @NotNull
    private UUID paymentId;

    @NotNull
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @NotNull
    private PaymentStatus status;

    public PaymentHistoryDto() {
    }

    public PaymentHistoryDto(UUID id, UUID paymentId, LocalDateTime date, PaymentStatus status) {
        this.id = id;
        this.paymentId = paymentId;
        this.date = date;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
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
