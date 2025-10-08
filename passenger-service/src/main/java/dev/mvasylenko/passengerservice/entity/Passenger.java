package dev.mvasylenko.passengerservice.entity;

import dev.mvasylenko.core.model.User;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "passengers")
public class Passenger extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    public Passenger() {
        super();
    }

    public Passenger(String name, String password, String email, String phone, UUID id) {
        super(name, password, email, phone);
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
