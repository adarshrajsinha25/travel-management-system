package com.easetravel.trip.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TripRequest {

    @NotBlank(message = "Trip name is required")
    private String name;

    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @NotNull(message = "Available seats is required")
    @Positive(message = "Available seats must be positive")
    private Integer availableSeats;

    @NotNull(message = "Departure date is required")
    @FutureOrPresent(message = "Departure date must be in the future")
    private LocalDate departureDate;

    private LocalDate returnDate;

    private String imageUrl;
}

