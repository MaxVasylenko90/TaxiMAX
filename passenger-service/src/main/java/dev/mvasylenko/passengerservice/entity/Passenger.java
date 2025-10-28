package dev.mvasylenko.passengerservice.entity;

import dev.mvasylenko.core.enums.Role;
import dev.mvasylenko.core.model.User;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "passenger")
public class Passenger extends User {

    public Passenger() {
        super();
    }

    public Passenger(String name, String surname, String password, String email, String phone, BigDecimal amount) {
        super(name, surname, password, email, phone, amount);
    }

    public Passenger(String email, String name) {
        super(email,name, Role.PASSENGER);
    }
}
