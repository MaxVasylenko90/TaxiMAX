package dev.mvasylenko.core.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class DriverRegistrationDto {
    @NotBlank(message = "Name is required!")
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank(message = "Surname is required!")
    @Size(min = 2, max = 50)
    private String surname;

    @NotBlank(message = "Password is required!")
    @Size(min = 2, max = 50)
    private String password;

    @NotBlank(message = "Email is required!")
    @Email
    @Size(min = 5, max = 50)
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{9}$", message = "Number should contain only 9 digits from 0 to 9")
    private String phone;

    @Size(min = 2, max = 20)
    @NotBlank
    private String driverLicenceNumber;

    public DriverRegistrationDto() {
    }

    public DriverRegistrationDto(String name, String surname, String password, String email, String phone, String driverLicenceNumber) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.driverLicenceNumber = driverLicenceNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDriverLicenceNumber() {
        return driverLicenceNumber;
    }

    public void setDriverLicenceNumber(String driverLicenceNumber) {
        this.driverLicenceNumber = driverLicenceNumber;
    }
}
