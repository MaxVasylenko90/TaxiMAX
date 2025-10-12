package dev.mvasylenko.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class CarDto {

    private UUID id;

    @NotBlank(message = "Car number is required!")
    @Size(max = 10, message = "Maximum 10 characters!")
    private String carNumber;

    @NotBlank(message = "Brand is required!")
    @Size(max = 20, message = "Maximum 20 characters!")
    private String brand;

    @NotBlank(message = "Model is required!")
    @Size(max = 20, message = "Maximum 20 characters!")
    private String model;

    @NotNull(message = "Color is required!")
    @Size(max = 20, message = "Maximum 20 characters!")
    private String color;

    private boolean available;

    private UUID driverId;

    public CarDto() {
    }

    public CarDto(String carNumber, String brand, String model, String color, boolean available, UUID driverId) {
        this.carNumber = carNumber;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.available = available;
        this.driverId = driverId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
