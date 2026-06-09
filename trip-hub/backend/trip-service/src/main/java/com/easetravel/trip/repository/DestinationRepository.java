package com.easetravel.trip.repository;

import com.easetravel.trip.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    List<Destination> findByCountryIgnoreCase(String country);
    List<Destination> findByNameContainingIgnoreCase(String name);
}

