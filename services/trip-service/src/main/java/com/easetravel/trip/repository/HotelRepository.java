package com.easetravel.trip.repository;

import com.easetravel.trip.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByCityIgnoreCase(String city);
    List<Hotel> findByStarsGreaterThanEqual(Integer stars);
}

