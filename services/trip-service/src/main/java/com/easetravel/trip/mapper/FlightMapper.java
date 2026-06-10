package com.easetravel.trip.mapper;

import com.easetravel.trip.dto.request.FlightRequest;
import com.easetravel.trip.dto.response.FlightResponse;
import com.easetravel.trip.entity.Flight;
import org.springframework.stereotype.Component;

@Component
public class FlightMapper {
    public FlightResponse toResponse(Flight flight) {
        return FlightResponse.builder()
                .id(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .airline(flight.getAirline())
                .origin(flight.getOrigin())
                .destination(flight.getDestination())
                .departureTime(flight.getDepartureTime())
                .arrivalTime(flight.getArrivalTime())
                .price(flight.getPrice())
                .availableSeats(flight.getAvailableSeats())
                .build();
    }

    public Flight toEntity(FlightRequest request) {
        return Flight.builder()
                .flightNumber(request.getFlightNumber())
                .airline(request.getAirline())
                .origin(request.getOrigin())
                .destination(request.getDestination())
                .departureTime(request.getDepartureTime())
                .arrivalTime(request.getArrivalTime())
                .price(request.getPrice())
                .availableSeats(request.getAvailableSeats())
                .build();
    }
}

