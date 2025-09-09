package com.EaseTravel.travel_management_system.mapper;

import org.springframework.stereotype.Component;
import com.EaseTravel.travel_management_system.model.entity.Booking;
import com.EaseTravel.travel_management_system.model.dto.response.BookingResponse;

@Component
public class BookingMapper {
    public BookingResponse toResponse(Booking booking) {
        BookingResponse response = new BookingResponse();
        // Map fields from booking to response here
        return response;
    }
}
