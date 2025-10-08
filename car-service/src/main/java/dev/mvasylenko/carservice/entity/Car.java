package dev.mvasylenko.carservice.entity;

import dev.mvasylenko.core.enums.Color;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String carNumber;

    private String brand;

    private String model;

    private Color color;

    private boolean available;

    private UUID driverId;

    public Car() {
    }

    public Car(String carNumber, String brand, String model, Color color, boolean available) {
        this.carNumber = carNumber;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.available = available;
    }

    public Car(String carNumber, String brand, String model, Color color, boolean available, UUID driverId) {
        this.carNumber = carNumber;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.available = available;
        this.driverId = driverId;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public UUID getId() {
        return id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public UUID getDriverId() {
        return driverId;
    }

    public void setDriverId(UUID driverId) {
        this.driverId = driverId;
    }
}
