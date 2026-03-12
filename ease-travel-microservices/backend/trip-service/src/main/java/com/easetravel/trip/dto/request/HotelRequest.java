package com.easetravel.trip.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class HotelRequest {

    @NotBlank(message = "Hotel name is required")
    private String name;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "City is required")
    private String city;

    @NotNull(message = "Stars rating is required")
    @Min(value = 1, message = "Stars must be between 1 and 5")
    @Max(value = 5, message = "Stars must be between 1 and 5")
    private Integer stars;

    @NotNull(message = "Price per night is required")
    @Positive(message = "Price per night must be positive")
    private BigDecimal pricePerNight;

    @NotNull(message = "Available rooms is required")
    @PositiveOrZero(message = "Available rooms must be non-negative")
    private Integer availableRooms;
}

