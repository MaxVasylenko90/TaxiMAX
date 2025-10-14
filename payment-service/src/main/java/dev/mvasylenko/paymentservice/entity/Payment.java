package dev.mvasylenko.paymentservice.entity;

import dev.mvasylenko.core.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "amount")
    @DecimalMin(value = "0.00")
    @NotNull
    private BigDecimal amount;

    @Column(name = "sender_id")
    @NotNull
    private UUID senderId;

    @Column(name = "receiver_id")
    @NotNull
    private UUID receiverId;

    @Column(name = "date")
    @NotNull
    private LocalDateTime date;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private PaymentStatus status;

    public Payment() {
    }

    public Payment(BigDecimal amount, UUID senderId, UUID receiverId, LocalDateTime date, PaymentStatus status) {
        this.amount = amount;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.date = date;
        this.status = status;
    }

    public UUID getId() {
        return id;
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
