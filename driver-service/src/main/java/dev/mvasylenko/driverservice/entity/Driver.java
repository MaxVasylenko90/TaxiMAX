package dev.mvasylenko.driverservice.entity;

import dev.mvasylenko.core.enums.DriverStatus;
import dev.mvasylenko.core.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Table(name = "driver")
public class Driver extends User {

    @Column(name = "driver_licence_number")
    @Size(min = 2, max = 20)
    @NotBlank
    private String driverLicenceNumber;

    @Column(name = "car_id")
    private UUID carId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private DriverStatus status = DriverStatus.OFFLINE;

    public Driver() {
        super();
    }

    public Driver(String name, String surname, String password, String email, String phone,
                  BigDecimal amount, String driverLicenceNumber, UUID carId) {
        super(name, surname, password, email, phone, amount);
        this.driverLicenceNumber = driverLicenceNumber;
        this.carId = carId;
    }

    public String getDriverLicenceNumber() {
        return driverLicenceNumber;
    }

    public void setDriverLicenceNumber(String driverLicenceNumber) {
        this.driverLicenceNumber = driverLicenceNumber;
    }

    public UUID getCarId() {
        return carId;
    }

    public void setCarId(UUID carId) {
        this.carId = carId;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }
}
