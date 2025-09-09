package com.EaseTravel.travel_management_system.repository;

import com.EaseTravel.travel_management_system.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Custom query methods if needed
}
