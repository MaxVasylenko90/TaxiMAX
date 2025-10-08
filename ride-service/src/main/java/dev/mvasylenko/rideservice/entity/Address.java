package dev.mvasylenko.rideservice.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String city;

    private String street;

    private String streetNumber;

    public Address() {
    }

    public Address(String city, String street, String streetNumber) {
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
    }

    public UUID getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }
}
