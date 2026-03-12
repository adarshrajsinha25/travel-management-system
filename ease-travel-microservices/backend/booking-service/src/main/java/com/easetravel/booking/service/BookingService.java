package com.easetravel.booking.service;

import com.easetravel.booking.dto.request.BookingRequest;
import com.easetravel.booking.dto.response.BookingResponse;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request);
    BookingResponse getBookingById(Long id);
    List<BookingResponse> getAllBookings();
    List<BookingResponse> getBookingsByUser(Long userId);
    BookingResponse confirmBooking(Long id);
    BookingResponse cancelBooking(Long id);
}

