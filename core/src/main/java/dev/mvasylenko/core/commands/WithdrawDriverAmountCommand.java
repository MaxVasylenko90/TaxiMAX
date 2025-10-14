package dev.mvasylenko.core.commands;

import java.math.BigDecimal;
import java.util.UUID;

public class WithdrawDriverAmountCommand {
    private UUID driverId;
    private BigDecimal amount;
    private UUID paymentId;

    public WithdrawDriverAmountCommand() {
    }

    public WithdrawDriverAmountCommand(UUID driverId, BigDecimal amount, UUID paymentId) {
        this.driverId = driverId;
        this.amount = amount;
        this.paymentId = paymentId;
    }

    public UUID getDriverId() {
        return driverId;
    }

    public void setDriverId(UUID driverId) {
        this.driverId = driverId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }
}
