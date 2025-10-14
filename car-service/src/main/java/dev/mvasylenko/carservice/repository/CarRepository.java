package dev.mvasylenko.carservice.repository;

import dev.mvasylenko.carservice.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {

    @Modifying(flushAutomatically = true)
    @Query("UPDATE Car SET available = false, driverId = :driverId WHERE id = :carId AND available = true")
    Integer reserveCarForDriver(UUID carId, UUID driverId);

    Set<Car> findAllByAvailable(boolean available);
}
