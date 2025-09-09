package com.EaseTravel.travel_management_system.mapper;

import org.springframework.stereotype.Component;
import com.EaseTravel.travel_management_system.model.entity.Payment;
import com.EaseTravel.travel_management_system.model.dto.response.BookingResponse;

@Component
public class PaymentMapper {
    public BookingResponse toResponse(Payment payment) {
        BookingResponse response = new BookingResponse();
        // Map fields from payment to response here
        return response;
    }
}
