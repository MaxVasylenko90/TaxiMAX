package dev.mvasylenko.paymentservice.entity;

import dev.mvasylenko.core.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payment_history")
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "payment_id")
    @NotNull
    private UUID paymentId;

    @Column(name = "date")
    @NotNull
    private LocalDateTime date;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private PaymentStatus status;

    public PaymentHistory() {
    }

    public PaymentHistory(UUID paymentId, LocalDateTime date, PaymentStatus status) {
        this.paymentId = paymentId;
        this.date = date;
        this.status = status;
    }

    public UUID getId() {
        return id;
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
