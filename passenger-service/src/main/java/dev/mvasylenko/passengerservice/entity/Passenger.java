package dev.mvasylenko.passengerservice.entity;

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

    public Passenger(String name, String surname, String password, String email, String phone, BigDecimal amount) {
        super(name, surname, password, email, phone, amount);
    }
}
