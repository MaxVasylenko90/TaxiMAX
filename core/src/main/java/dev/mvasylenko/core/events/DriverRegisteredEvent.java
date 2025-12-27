package dev.mvasylenko.core.events;

import dev.mvasylenko.core.dto.CarDto;
import dev.mvasylenko.core.enums.Role;

import java.util.UUID;

public class DriverRegisteredEvent {
    private UUID eventId = UUID.randomUUID();
    private UUID userId;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private Role role;
    private String drivingLicense;
    private CarDto car;

    public DriverRegisteredEvent() {
    }

    public DriverRegisteredEvent(UUID userId, String name, String surname, String email, String phone, Role role, String  drivingLicense, CarDto car) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.drivingLicense = drivingLicense;
        this.car = car;
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public CarDto getCar() {
        return car;
    }

    public void setCar(CarDto car) {
        this.car = car;
    }
}
