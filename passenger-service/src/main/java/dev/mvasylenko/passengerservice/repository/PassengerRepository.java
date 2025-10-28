package dev.mvasylenko.passengerservice.repository;

import dev.mvasylenko.passengerservice.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, UUID> {
    Passenger findByEmail(String email);
}
