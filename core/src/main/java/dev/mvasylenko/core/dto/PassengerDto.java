package dev.mvasylenko.core.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class PassengerDto {

    private UUID id;

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

    @DecimalMin("0.00")
    private BigDecimal amount;

    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{9}$", message = "Number should contain only 9 digits from 0 to 9")
    private String phone;

    @DecimalMin("0.00")
    @DecimalMax("5.00")
    @NotNull
    private BigDecimal averageRating;

    private List<String> comments;

    private Set<UUID> rideHistory;

    public PassengerDto() {
        super();
    }

    public PassengerDto(UUID id, String name, String surname, String password, String email, BigDecimal amount,
                        String phone, BigDecimal averageRating, List<String> comments, Set<UUID> rideHistory) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.amount = amount;
        this.phone = phone;
        this.averageRating = averageRating;
        this.comments = comments;
        this.rideHistory = rideHistory;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(BigDecimal averageRating) {
        this.averageRating = averageRating;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public Set<UUID> getRideHistory() {
        return rideHistory;
    }

    public void setRideHistory(Set<UUID> rideHistory) {
        this.rideHistory = rideHistory;
    }
}
