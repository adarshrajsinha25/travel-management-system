package com.easetravel.trip.repository;

import com.easetravel.trip.entity.Trip;
import com.easetravel.trip.enums.TripStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByStatus(TripStatus status);
    List<Trip> findByPriceLessThanEqual(BigDecimal maxPrice);
}

