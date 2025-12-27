package dev.mvasylenko.passengerservice.entity;

import dev.mvasylenko.core.enums.Role;
import dev.mvasylenko.core.model.User;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "passenger")
public class Passenger extends User {

    public Passenger() {
        super();
    }

    public Passenger(UUID id, String name, String surname, String email, String phone) {
        super(id, name, surname, email, phone, BigDecimal.ZERO, Role.PASSENGER);
    }
}
