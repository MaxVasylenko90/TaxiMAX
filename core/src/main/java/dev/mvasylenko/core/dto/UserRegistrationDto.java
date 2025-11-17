package dev.mvasylenko.core.dto;

import dev.mvasylenko.core.enums.Role;
import dev.mvasylenko.core.model.DriverInfo;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.util.UUID;

public class UserRegistrationDto {
    private UUID id = UUID.randomUUID();

    @NotBlank(message = "Name is required!")
    @Size(min = 2, max = 50)
    private String name;

    @NotNull(message = "Surname is required!")
    @Size(min = 2, max = 50)
    private String surname;

    @Size(min = 8, max = 50, message = "Password must contain between 8 and 50 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,50}$",
            message = "Password must contain uppercase, lowercase, number and special character"
    )
    private String password;

    @NotBlank(message = "Email is required!")
    @Email
    @Size(min = 5, max = 50)
    private String email;

    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{9}$", message = "Number should contain only 9 digits from 0 to 9")
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    private DriverInfo driverInfo;

    public UserRegistrationDto() {
    }

    public UserRegistrationDto(String name, String surname, String password, String email,
                               String phone, DriverInfo driverInfo) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.driverInfo = driverInfo;
    }

    public UserRegistrationDto(String name, String surname, String password, String email, String phone) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.driverInfo = null;
    }

    public UserRegistrationDto(String email, String name) {
        this.email = email;
        this.name = name;
        this.role = Role.PASSENGER;
        this.surname = null;
        this.password = null;
        this.phone = null;
        this.driverInfo = null;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public DriverInfo getDriverInfo() {
        return driverInfo;
    }

    public void setDriver(DriverInfo driverInfo) {
        this.driverInfo = driverInfo;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setDriverInfo(DriverInfo driverInfo) {
        this.driverInfo = driverInfo;
    }
}
