package com.easetravel.booking.service.impl;

import com.easetravel.booking.client.TripServiceClient;
import com.easetravel.booking.dto.request.BookingRequest;
import com.easetravel.booking.dto.response.ApiResponse;
import com.easetravel.booking.dto.response.BookingResponse;
import com.easetravel.booking.dto.response.TripResponse;
import com.easetravel.booking.entity.Booking;
import com.easetravel.booking.enums.BookingStatus;
import com.easetravel.booking.exception.BookingException;
import com.easetravel.booking.exception.ResourceNotFoundException;
import com.easetravel.booking.mapper.BookingMapper;
import com.easetravel.booking.repository.BookingRepository;
import com.easetravel.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final TripServiceClient tripServiceClient;
    private final BookingMapper bookingMapper;

    @Override
    public BookingResponse createBooking(BookingRequest request) {
        // Fetch trip details via Feign
        ApiResponse<TripResponse> tripApiResponse = tripServiceClient.getTripById(request.getTripId());
        if (tripApiResponse == null || !tripApiResponse.isSuccess() || tripApiResponse.getData() == null) {
            throw new BookingException("Trip not found with id: " + request.getTripId());
        }
        TripResponse trip = tripApiResponse.getData();

        if ("FULLY_BOOKED".equals(trip.getStatus()) || "CANCELLED".equals(trip.getStatus())) {
            throw new BookingException("Trip is not available for booking. Status: " + trip.getStatus());
        }
        if (trip.getAvailableSeats() < request.getNumberOfGuests()) {
            throw new BookingException("Not enough seats. Available: " + trip.getAvailableSeats());
        }

        BigDecimal totalAmount = trip.getPrice().multiply(BigDecimal.valueOf(request.getNumberOfGuests()));

        Booking booking = Booking.builder()
                .userId(request.getUserId())
                .tripId(request.getTripId())
                .numberOfGuests(request.getNumberOfGuests())
                .totalAmount(totalAmount)
                .build();

        Booking saved = bookingRepository.save(booking);

        // Decrement seats in trip-service
        tripServiceClient.decrementSeats(request.getTripId(), request.getNumberOfGuests());

        log.info("Booking created: id={}, user={}, trip={}", saved.getId(), saved.getUserId(), saved.getTripId());
        return bookingMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingResponse getBookingById(Long id) {
        return bookingMapper.toResponse(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll().stream().map(bookingMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponse> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId).stream()
                .map(bookingMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public BookingResponse confirmBooking(Long id) {
        Booking booking = findOrThrow(id);
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BookingException("Cannot confirm a cancelled booking");
        }
        booking.setStatus(BookingStatus.CONFIRMED);
        return bookingMapper.toResponse(bookingRepository.save(booking));
    }

    @Override
    public BookingResponse cancelBooking(Long id) {
        Booking booking = findOrThrow(id);
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BookingException("Booking is already cancelled");
        }
        booking.setStatus(BookingStatus.CANCELLED);
        return bookingMapper.toResponse(bookingRepository.save(booking));
    }

    private Booking findOrThrow(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
    }
}

