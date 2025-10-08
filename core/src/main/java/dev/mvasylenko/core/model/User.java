package dev.mvasylenko.core.model;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class User {
    private String name;
    private String password;
    private String email;
    private String phone;
    private AtomicInteger starsRating;
    private List<String> comments;
    private LinkedHashSet<UUID> rideHistory;

    public User() {
    }

    public User(String name, String password, String email, String phone) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.starsRating = new AtomicInteger(0);
        this.comments = new LinkedList<>();
        this.rideHistory = new LinkedHashSet<>();
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

    public AtomicInteger getStarsRating() {
        return starsRating;
    }

    public void setStarsRating(AtomicInteger starsRating) {
        this.starsRating = starsRating;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public LinkedHashSet<UUID> getRideHistory() {
        return rideHistory;
    }

    public void setRideHistory(LinkedHashSet<UUID> rideHistory) {
        this.rideHistory = rideHistory;
    }
}
