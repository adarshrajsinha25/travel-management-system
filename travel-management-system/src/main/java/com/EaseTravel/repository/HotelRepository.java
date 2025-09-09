package com.EaseTravel.travel_management_system.repository;

import com.EaseTravel.travel_management_system.model.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    // Custom query methods if needed
}
