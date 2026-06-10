package com.easetravel.trip.service;

import com.easetravel.trip.dto.request.DestinationRequest;
import com.easetravel.trip.dto.response.DestinationResponse;

import java.util.List;

public interface DestinationService {
    DestinationResponse createDestination(DestinationRequest request);
    DestinationResponse getDestinationById(Long id);
    List<DestinationResponse> getAllDestinations();
    List<DestinationResponse> findByCountry(String country);
    DestinationResponse updateDestination(Long id, DestinationRequest request);
    void deleteDestination(Long id);
}

