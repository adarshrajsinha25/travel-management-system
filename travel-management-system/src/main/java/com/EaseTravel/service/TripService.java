package com.EaseTravel.service;

import com.EaseTravel.model.entity.Trip;
import java.util.List;

public interface TripService {
    Trip createTrip(Trip trip);
    Trip getTripById(Long id);
    List<Trip> getAllTrips();
    Trip updateTrip(Long id, Trip trip);
    void deleteTrip(Long id);
}
