package dev.mvasylenko.core.events;

import dev.mvasylenko.core.enums.Role;
import dev.mvasylenko.core.model.DriverInfo;

import java.util.UUID;

public interface UserRegisteredEvent {
    void setUserId(UUID userId);
    void setName(String name);
    void setSurname(String surname);
    void setEmail(String email);
    void setPhone(String phone);
    void setRole(Role role);
    void setDriverInfo(DriverInfo driverInfo);
}
