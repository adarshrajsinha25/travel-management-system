package com.EaseTravel.service.impl;

import com.EaseTravel.travel_management_system.model.entity.Trip;
import com.EaseTravel.travel_management_system.repository.TripRepository;
import com.EaseTravel.travel_management_system.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TripServiceImpl implements TripService {
    @Autowired
    private TripRepository tripRepository;

    @Override
    public Trip createTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    @Override
    public Trip getTripById(Long id) {
        return tripRepository.findById(id).orElse(null);
    }

    @Override
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    @Override
    public Trip updateTrip(Long id, Trip trip) {
        Optional<Trip> existing = tripRepository.findById(id);
        if (existing.isPresent()) {
            Trip t = existing.get();
            t.setDestination(trip.getDestination());
            t.setDescription(trip.getDescription());
            t.setPrice(trip.getPrice());
            return tripRepository.save(t);
        }
        return null;
    }

    @Override
    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }
}
