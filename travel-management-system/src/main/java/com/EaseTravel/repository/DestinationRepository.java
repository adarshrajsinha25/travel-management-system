package com.EaseTravel.travel_management_system.repository;

import com.EaseTravel.travel_management_system.model.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
    // Custom query methods if needed
}
