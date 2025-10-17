package dev.mvasylenko.core.commands;

import java.util.UUID;

public class CancelCarReservationCommand {
    private UUID commandId = UUID.randomUUID();
    private UUID carId;
    private UUID senderId;

    public CancelCarReservationCommand() {
    }

    public CancelCarReservationCommand(UUID carId, UUID senderId) {
        this.carId = carId;
        this.senderId = senderId;
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

    public UUID getSenderId() {
        return senderId;
    }

    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }
}
