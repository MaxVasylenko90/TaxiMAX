package dev.mvasylenko.core.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.*;

@MappedSuperclass
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    @NotBlank(message = "Name is required!")
    @Size(min = 2, max = 50)
    private String name;

    @Column(name = "surname")
    @NotBlank(message = "Surname is required!")
    @Size(min = 2, max = 50)
    private String surname;

    @Column(name = "password")
    @NotBlank(message = "Password is required!")
    @Size(min = 2, max = 50)
    private String password;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email is required!")
    @Email
    @Size(min = 5, max = 50)
    private String email;

    @Column(name = "amount", precision = 10, scale = 2, nullable = false)
    @DecimalMin(value = "0.00")
    private BigDecimal amount = BigDecimal.ZERO;;

    @Column(name = "phone")
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{9}$", message = "Number should contain only 9 digits from 0 to 9")
    private String phone;

    @Column(name = "average_rating", precision = 3, scale = 2, nullable = false)
    @DecimalMin(value = "0.00")
    @DecimalMax(value = "5.00")
    @NotNull
    private BigDecimal averageRating = BigDecimal.ZERO;

    @Column(name = "comment")
    @ElementCollection
    @CollectionTable(
            name = "user_comments",
            joinColumns = @JoinColumn(name = "user_id")
    )
    private List<String> comments = new ArrayList<>();

    @Column(name = "ride_id")
    @ElementCollection
    @CollectionTable(
            name = "user_ride_history",
            joinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UUID> rideHistory = new LinkedHashSet<>();

    public User() {
    }

    public User(String name, String surname, String password, String email, String phone, BigDecimal amount) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public BigDecimal getAverageRating() {
        return averageRating;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setAverageRating(BigDecimal averageRating) {
        this.averageRating = averageRating;
    }

    public Set<UUID> getRideHistory() {
        return rideHistory;
    }

    public void setRideHistory(Set<UUID> rideHistory) {
        this.rideHistory = rideHistory;
    }

    public UUID getId() {
        return id;
    }
}
