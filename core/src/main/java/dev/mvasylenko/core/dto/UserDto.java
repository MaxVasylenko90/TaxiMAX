package dev.mvasylenko.core.dto;

import dev.mvasylenko.core.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class UserDto {
    @NotNull
    private UUID id;

    @Column(name = "email")
    @Email
    @NotBlank
    private String email;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    public UserDto() {
    }

    public UserDto(UUID id, String email, Role role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
