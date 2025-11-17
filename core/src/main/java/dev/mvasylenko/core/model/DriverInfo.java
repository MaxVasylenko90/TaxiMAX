package dev.mvasylenko.core.model;

import dev.mvasylenko.core.dto.CarDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DriverInfo {
    @Size(min = 2, max = 20)
    @NotBlank
    private String driverLicenceNumber;

    private CarDto car;

    public DriverInfo(String driverLicenceNumber, CarDto car) {
        this.driverLicenceNumber = driverLicenceNumber;
        this.car = car;
    }

    public String getDriverLicenceNumber() {
        return driverLicenceNumber;
    }

    public void setDriverLicenceNumber(String driverLicenceNumber) {
        this.driverLicenceNumber = driverLicenceNumber;
    }

    public CarDto getCar() {
        return car;
    }

    public void setCar(CarDto car) {
        this.car = car;
    }
}
