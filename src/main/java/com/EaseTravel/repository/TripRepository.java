package com.EaseTravel.repository;

import com.EaseTravel.model.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
    // Custom query methods if needed
}
