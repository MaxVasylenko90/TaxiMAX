package dev.mvasylenko.carservice.repository;

import dev.mvasylenko.carservice.entity.CarRentalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarRentalHistoryRepository extends JpaRepository<CarRentalHistory, UUID> {

    List<CarRentalHistory> findByCarId(UUID carId);
}
