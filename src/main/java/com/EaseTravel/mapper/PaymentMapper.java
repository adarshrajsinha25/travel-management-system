package com.EaseTravel.mapper;

import org.springframework.stereotype.Component;
import com.EaseTravel.model.entity.Payment;
import com.EaseTravel.model.dto.response.BookingResponse;

@Component
public class PaymentMapper {
    public BookingResponse toResponse(Payment payment) {
        BookingResponse response = new BookingResponse();
        // Map fields from payment to response here
        return response;
    }
}
