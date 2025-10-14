package dev.mvasylenko.core.commands;

import java.math.BigDecimal;
import java.util.UUID;

public class CreatePaymentCommand {
    private UUID driverId;
    private BigDecimal carRentPrice;

    public CreatePaymentCommand() {
    }

    public CreatePaymentCommand(UUID driverId, BigDecimal carRentPrice) {
        this.driverId = driverId;
        this.carRentPrice = carRentPrice;
    }

    public UUID getDriverId() {
        return driverId;
    }

    public void setDriverId(UUID driverId) {
        this.driverId = driverId;
    }

    public BigDecimal getCarRentPrice() {
        return carRentPrice;
    }

    public void setCarRentPrice(BigDecimal carRentPrice) {
        this.carRentPrice = carRentPrice;
    }
}
