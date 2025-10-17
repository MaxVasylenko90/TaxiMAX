package dev.mvasylenko.core.commands;

import java.util.UUID;

public class AssignCarToDriverCommand {
    private UUID commandId = UUID.randomUUID();
    private UUID carId;
    private UUID driverId;

    public AssignCarToDriverCommand() {
    }

    public AssignCarToDriverCommand(UUID carId, UUID driverId) {
        this.carId = carId;
        this.driverId = driverId;
    }

    public UUID getCommandId() {
        return commandId;
    }

    public void setCommandId(UUID commandId) {
        this.commandId = commandId;
    }

    public UUID getCarId() {
        return carId;
    }

    public void setCarId(UUID carId) {
        this.carId = carId;
    }

    public UUID getDriverId() {
        return driverId;
    }

    public void setDriverId(UUID driverId) {
        this.driverId = driverId;
    }
}
