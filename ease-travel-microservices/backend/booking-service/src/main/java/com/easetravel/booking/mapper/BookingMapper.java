package com.easetravel.booking.mapper;

import com.easetravel.booking.dto.response.BookingResponse;
import com.easetravel.booking.entity.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    public BookingResponse toResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .userId(booking.getUserId())
                .tripId(booking.getTripId())
                .numberOfGuests(booking.getNumberOfGuests())
                .totalAmount(booking.getTotalAmount())
                .status(booking.getStatus().name())
                .bookingDate(booking.getBookingDate())
                .build();
    }
}

