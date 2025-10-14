package dev.mvasylenko.core.commands;

import java.util.UUID;

public class CancelCarReservationCommand {
    private UUID carId;

    public CancelCarReservationCommand() {
    }

    public CancelCarReservationCommand(UUID carId) {
        this.carId = carId;
    }

    public UUID getCarId() {
        return carId;
    }

    public void setCarId(UUID carId) {
        this.carId = carId;
    }
}
