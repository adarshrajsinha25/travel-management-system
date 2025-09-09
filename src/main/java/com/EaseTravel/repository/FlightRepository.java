package com.EaseTravel.repository;

import com.EaseTravel.model.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    // Custom query methods if needed
}
