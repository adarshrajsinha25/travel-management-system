package com.EaseTravel.mapper;

import org.springframework.stereotype.Component;
import com.EaseTravel.model.entity.Booking;
import com.EaseTravel.model.dto.response.BookingResponse;

@Component
public class BookingMapper {
    public BookingResponse toResponse(Booking booking) {
        BookingResponse response = new BookingResponse();
        // Map fields from booking to response here
        return response;
    }
}
