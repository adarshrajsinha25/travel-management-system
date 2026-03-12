package com.easetravel.trip.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class HotelResponse {
    private Long id;
    private String name;
    private String address;
    private String city;
    private Integer stars;
    private BigDecimal pricePerNight;
    private Integer availableRooms;
}

