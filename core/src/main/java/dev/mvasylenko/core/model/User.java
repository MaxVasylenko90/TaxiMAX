package dev.mvasylenko.core.model;

import dev.mvasylenko.core.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.*;

@MappedSuperclass
public abstract class User {
    @Id
    private UUID id;

    @Column(name = "name")
    @NotBlank(message = "Name is required!")
    @Size(min = 2, max = 50)
    private String name;

    @Column(name = "surname")
    @NotNull(message = "Surname is required!")
    @Size(min = 2, max = 50)
    private String surname;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email is required!")
    @Email
    @Size(min = 5, max = 50)
    private String email;

    @Column(name = "amount", precision = 10, scale = 2, nullable = false)
    @DecimalMin(value = "0.00")
    private BigDecimal amount = BigDecimal.ZERO;;

    @Column(name = "phone")
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

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @Column(name = "is_account_completed")
    private boolean isAccountCompleted;

    public User() {
    }

    public User(UUID id, String name, String surname, String email, String phone, BigDecimal amount, Role role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.amount = amount;
        this.role = role;
        isAccountCompleted = List.of(id, name, surname, email, phone, amount, role).stream()
                .allMatch(Objects::nonNull);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isAccountCompleted() {
        return isAccountCompleted;
    }

    public void setAccountCompleted(boolean accountCompleted) {
        isAccountCompleted = accountCompleted;
    }
}
