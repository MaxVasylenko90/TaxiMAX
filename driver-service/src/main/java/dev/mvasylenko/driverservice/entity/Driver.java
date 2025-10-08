package dev.mvasylenko.driverservice.entity;

import dev.mvasylenko.core.model.User;
import jakarta.persistence.*;

import java.util.UUID;


@Entity
@Table(name = "drivers")
public class Driver extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String driverLicenceNumber;

    private UUID carId;

    public Driver() {
        super();
    }

    public Driver(String name, String password, String email, String phone, String driverLicenceNumber, UUID carId) {
        super(name, password, email, phone);
        this.driverLicenceNumber = driverLicenceNumber;
        this.carId = carId;
    }

    public UUID getId() {
        return id;
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
}
