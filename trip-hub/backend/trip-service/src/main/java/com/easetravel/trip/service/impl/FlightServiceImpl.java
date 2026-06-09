package com.easetravel.trip.service.impl;

import com.easetravel.trip.dto.request.FlightRequest;
import com.easetravel.trip.dto.response.FlightResponse;
import com.easetravel.trip.entity.Flight;
import com.easetravel.trip.exception.ResourceNotFoundException;
import com.easetravel.trip.mapper.FlightMapper;
import com.easetravel.trip.repository.FlightRepository;
import com.easetravel.trip.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    @Override
    public FlightResponse createFlight(FlightRequest request) {
        return flightMapper.toResponse(flightRepository.save(flightMapper.toEntity(request)));
    }

    @Override
    @Transactional(readOnly = true)
    public FlightResponse getFlightById(Long id) {
        return flightMapper.toResponse(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FlightResponse> getAllFlights() {
        return flightRepository.findAll().stream().map(flightMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FlightResponse> searchFlights(String origin, String destination) {
        return flightRepository.findByOriginIgnoreCaseAndDestinationIgnoreCase(origin, destination)
                .stream().map(flightMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public FlightResponse updateFlight(Long id, FlightRequest request) {
        Flight flight = findOrThrow(id);
        flight.setFlightNumber(request.getFlightNumber());
        flight.setAirline(request.getAirline());
        flight.setOrigin(request.getOrigin());
        flight.setDestination(request.getDestination());
        flight.setDepartureTime(request.getDepartureTime());
        flight.setArrivalTime(request.getArrivalTime());
        flight.setPrice(request.getPrice());
        flight.setAvailableSeats(request.getAvailableSeats());
        return flightMapper.toResponse(flightRepository.save(flight));
    }

    @Override
    public void deleteFlight(Long id) {
        if (!flightRepository.existsById(id)) throw new ResourceNotFoundException("Flight not found with id: " + id);
        flightRepository.deleteById(id);
    }

    private Flight findOrThrow(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id));
    }
}

