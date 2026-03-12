package com.easetravel.trip.service;

import com.easetravel.trip.dto.request.TripRequest;
import com.easetravel.trip.dto.response.TripResponse;

import java.util.List;

public interface TripService {
    TripResponse createTrip(TripRequest request);
    TripResponse getTripById(Long id);
    List<TripResponse> getAllTrips();
    List<TripResponse> getAvailableTrips();
    TripResponse updateTrip(Long id, TripRequest request);
    void deleteTrip(Long id);
    TripResponse decrementSeats(Long id, int count);
}

