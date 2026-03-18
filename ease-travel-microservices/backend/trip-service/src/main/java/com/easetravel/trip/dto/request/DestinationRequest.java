package com.easetravel.trip.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DestinationRequest {

    @NotBlank(message = "Destination name is required")
    private String name;

    @NotBlank(message = "Country is required")
    private String country;

    private String description;
    private String imageUrl;
}

