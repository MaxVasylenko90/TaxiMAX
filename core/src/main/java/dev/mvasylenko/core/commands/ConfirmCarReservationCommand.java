package dev.mvasylenko.core.commands;

import java.util.UUID;

public class ConfirmCarReservationCommand {
    private UUID commandId = UUID.randomUUID();
    private UUID carId;

    public ConfirmCarReservationCommand() {
    }

    public ConfirmCarReservationCommand(UUID carId) {
        this.carId = carId;
    }

    public UUID getCarId() {
        return carId;
    }

    public void setCarId(UUID carId) {
        this.carId = carId;
    }

    public UUID getCommandId() {
        return commandId;
    }

    public void setCommandId(UUID commandId) {
        this.commandId = commandId;
    }
}
