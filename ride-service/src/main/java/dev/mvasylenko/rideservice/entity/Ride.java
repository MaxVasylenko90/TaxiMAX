package dev.mvasylenko.rideservice.entity;

import dev.mvasylenko.core.enums.RideStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "rides")
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID driverId;

    private UUID passengerId;

    private UUID pickUpAddressId;

    private UUID dropOffAddressId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private RideStatus status;

    public Ride() {
    }

    public Ride(UUID driverId, UUID passengerId, UUID pickUpAddressId, UUID dropOffAddressId,
                LocalDateTime startTime, LocalDateTime endTime, RideStatus status) {
        this.driverId = driverId;
        this.passengerId = passengerId;
        this.pickUpAddressId = pickUpAddressId;
        this.dropOffAddressId = dropOffAddressId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public UUID getDriverId() {
        return driverId;
    }

    public void setDriverId(UUID driverId) {
        this.driverId = driverId;
    }

    public UUID getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(UUID passengerId) {
        this.passengerId = passengerId;
    }

    public UUID getPickUpAddressId() {
        return pickUpAddressId;
    }

    public void setPickUpAddressId(UUID pickUpAddressId) {
        this.pickUpAddressId = pickUpAddressId;
    }

    public UUID getDropOffAddressId() {
        return dropOffAddressId;
    }

    public void setDropOffAddressId(UUID dropOffAddressId) {
        this.dropOffAddressId = dropOffAddressId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }
}
