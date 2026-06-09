package com.easetravel.trip.service.impl;

import com.easetravel.trip.dto.request.TripRequest;
import com.easetravel.trip.dto.response.TripResponse;
import com.easetravel.trip.entity.Trip;
import com.easetravel.trip.enums.TripStatus;
import com.easetravel.trip.exception.InsufficientSeatsException;
import com.easetravel.trip.exception.ResourceNotFoundException;
import com.easetravel.trip.mapper.TripMapper;
import com.easetravel.trip.repository.TripRepository;
import com.easetravel.trip.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final TripMapper tripMapper;

    @Override
    public TripResponse createTrip(TripRequest request) {
        Trip trip = tripMapper.toEntity(request);
        return tripMapper.toResponse(tripRepository.save(trip));
    }

    @Override
    @Transactional(readOnly = true)
    public TripResponse getTripById(Long id) {
        return tripMapper.toResponse(findTripOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TripResponse> getAllTrips() {
        return tripRepository.findAll().stream()
                .map(tripMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TripResponse> getAvailableTrips() {
        return tripRepository.findByStatus(TripStatus.AVAILABLE).stream()
                .map(tripMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TripResponse updateTrip(Long id, TripRequest request) {
        Trip trip = findTripOrThrow(id);
        trip.setName(request.getName());
        trip.setDescription(request.getDescription());
        trip.setPrice(request.getPrice());
        trip.setAvailableSeats(request.getAvailableSeats());
        trip.setDepartureDate(request.getDepartureDate());
        trip.setReturnDate(request.getReturnDate());
        trip.setImageUrl(request.getImageUrl());
        return tripMapper.toResponse(tripRepository.save(trip));
    }

    @Override
    public void deleteTrip(Long id) {
        if (!tripRepository.existsById(id)) {
            throw new ResourceNotFoundException("Trip not found with id: " + id);
        }
        tripRepository.deleteById(id);
    }

    @Override
    public TripResponse decrementSeats(Long id, int count) {
        Trip trip = findTripOrThrow(id);
        if (trip.getAvailableSeats() < count) {
            throw new InsufficientSeatsException(
                    "Not enough seats. Available: " + trip.getAvailableSeats() + ", Requested: " + count);
        }
        trip.setAvailableSeats(trip.getAvailableSeats() - count);
        if (trip.getAvailableSeats() == 0) {
            trip.setStatus(TripStatus.FULLY_BOOKED);
        }
        return tripMapper.toResponse(tripRepository.save(trip));
    }

    private Trip findTripOrThrow(Long id) {
        return tripRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + id));
    }
}

