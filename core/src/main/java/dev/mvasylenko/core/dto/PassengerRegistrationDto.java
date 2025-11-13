package dev.mvasylenko.core.dto;

import jakarta.validation.constraints.*;

public class PassengerRegistrationDto {
    @NotBlank(message = "Name is required!")
    @Size(min = 2, max = 50)
    private String name;

    @NotNull(message = "Surname is required!")
    @Size(min = 2, max = 50)
    private String surname;

    @NotNull(message = "Password is required!")
    @Size(min = 2, max = 50)
    private String password;

    @NotBlank(message = "Email is required!")
    @Email
    @Size(min = 5, max = 50)
    private String email;

    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{9}$", message = "Number should contain only 9 digits from 0 to 9")
    private String phone;

    public PassengerRegistrationDto() {
    }

    public PassengerRegistrationDto(String name, String surname, String password, String email, String phone) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.phone = phone;
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
}
