package com.EaseTravel.repository;

import com.EaseTravel.model.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
    // Custom query methods if needed
}
