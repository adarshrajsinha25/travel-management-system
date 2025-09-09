package com.EaseTravel.travel_management_system.repository;

import com.EaseTravel.travel_management_system.model.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    // Custom query methods if needed
}
