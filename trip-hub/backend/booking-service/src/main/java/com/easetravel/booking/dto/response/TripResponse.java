package com.easetravel.booking.dto.response;

import lombok.Data;
import java.math.BigDecimal;

/** Mirror DTO for trip-service TripResponse (used by Feign client) */
@Data
public class TripResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer availableSeats;
    private String status;
}

