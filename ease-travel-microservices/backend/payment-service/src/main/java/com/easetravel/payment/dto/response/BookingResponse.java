package com.easetravel.payment.dto.response;

import lombok.Data;
import java.math.BigDecimal;

/** Mirror DTO for booking-service BookingResponse (used by Feign client) */
@Data
public class BookingResponse {
    private Long id;
    private Long userId;
    private Long tripId;
    private String status;
    private BigDecimal totalAmount;
}

