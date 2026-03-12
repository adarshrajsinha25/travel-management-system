package com.easetravel.trip.mapper;

import com.easetravel.trip.dto.request.TripRequest;
import com.easetravel.trip.dto.response.TripResponse;
import com.easetravel.trip.entity.Trip;
import org.springframework.stereotype.Component;

@Component
public class TripMapper {
    public TripResponse toResponse(Trip trip) {
        return TripResponse.builder()
                .id(trip.getId())
                .name(trip.getName())
                .description(trip.getDescription())
                .price(trip.getPrice())
                .availableSeats(trip.getAvailableSeats())
                .status(trip.getStatus().name())
                .departureDate(trip.getDepartureDate())
                .returnDate(trip.getReturnDate())
                .imageUrl(trip.getImageUrl())
                .createdAt(trip.getCreatedAt())
                .build();
    }

    public Trip toEntity(TripRequest request) {
        return Trip.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .availableSeats(request.getAvailableSeats())
                .departureDate(request.getDepartureDate())
                .returnDate(request.getReturnDate())
                .imageUrl(request.getImageUrl())
                .build();
    }
}

