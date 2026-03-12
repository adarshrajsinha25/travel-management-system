package com.easetravel.trip.service;

import com.easetravel.trip.dto.request.FlightRequest;
import com.easetravel.trip.dto.response.FlightResponse;

import java.util.List;

public interface FlightService {
    FlightResponse createFlight(FlightRequest request);
    FlightResponse getFlightById(Long id);
    List<FlightResponse> getAllFlights();
    List<FlightResponse> searchFlights(String origin, String destination);
    FlightResponse updateFlight(Long id, FlightRequest request);
    void deleteFlight(Long id);
}

