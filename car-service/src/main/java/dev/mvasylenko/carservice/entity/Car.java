package dev.mvasylenko.carservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "car_number")
    @NotBlank(message = "Car number is required!")
    @Size(max = 10, message = "Maximum 10 characters!")
    private String carNumber;

    @Column(name = "brand")
    @NotBlank(message = "Brand is required!")
    @Size(max = 20, message = "Maximum 20 characters!")
    private String brand;

    @Column(name = "model")
    @NotBlank(message = "Model is required!")
    @Size(max = 20, message = "Maximum 20 characters!")
    private String model;

    @Column(name = "color")
    @NotNull(message = "Color is required!")
    @Size(max = 20, message = "Maximum 20 characters!")
    private String color;

    @Column(name = "available")
    private boolean available;

    @Column(name = "driver_id")
    private UUID driverId;

    @Column(name = "rent_price")
    @DecimalMin(value = "0.00")
    private BigDecimal rentPrice;

    public Car() {
    }

    public Car(String carNumber, String brand, String model, String color, UUID driverId) {
        this.carNumber = carNumber;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.available = Boolean.FALSE;
        this.driverId = driverId;
    }

    public Car(String carNumber, String brand, String model, String color, BigDecimal rentPrice) {
        this.carNumber = carNumber;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.available = Boolean.TRUE;
        this.rentPrice = rentPrice;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
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

    public BigDecimal getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(BigDecimal rentPrice) {
        this.rentPrice = rentPrice;
    }
}
