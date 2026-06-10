package com.easetravel.trip.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class TripResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer availableSeats;
    private String status;
    private LocalDate departureDate;
    private LocalDate returnDate;
    private String imageUrl;
    private LocalDateTime createdAt;
}

