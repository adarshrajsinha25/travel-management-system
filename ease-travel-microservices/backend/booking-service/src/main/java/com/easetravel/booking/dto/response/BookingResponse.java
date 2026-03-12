package com.easetravel.booking.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class BookingResponse {
    private Long id;
    private Long userId;
    private Long tripId;
    private Integer numberOfGuests;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime bookingDate;
}

