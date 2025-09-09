package com.EaseTravel.repository;

import com.EaseTravel.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Custom query methods if needed
}
