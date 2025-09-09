package com.EaseTravel.repository;

import com.EaseTravel.model.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    // Custom query methods if needed
}
