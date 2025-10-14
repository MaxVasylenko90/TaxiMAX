package dev.mvasylenko.carservice.entity;

import dev.mvasylenko.core.enums.CarRentalStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "car_rental_history")
public class CarRentalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "car_id")
    @NotNull
    private UUID carId;

    @Column(name = "driver_id")
    @NotNull
    private UUID driverId;

    @Column(name = "status")
    @NotNull
    private CarRentalStatus status;

    @Column(name = "date")
    @NotNull
    private LocalDateTime date;

    public CarRentalHistory() {
    }

    public CarRentalHistory(UUID carId, UUID driverId, CarRentalStatus status, LocalDateTime date) {
        this.carId = carId;
        this.driverId = driverId;
        this.status = status;
        this.date = date;
    }

    public UUID getId() {
        return id;
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

    public CarRentalStatus getStatus() {
        return status;
    }

    public void setStatus(CarRentalStatus status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
