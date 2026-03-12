package com.easetravel.trip.mapper;

import com.easetravel.trip.dto.request.HotelRequest;
import com.easetravel.trip.dto.response.HotelResponse;
import com.easetravel.trip.entity.Hotel;
import org.springframework.stereotype.Component;

@Component
public class HotelMapper {
    public HotelResponse toResponse(Hotel hotel) {
        return HotelResponse.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .address(hotel.getAddress())
                .city(hotel.getCity())
                .stars(hotel.getStars())
                .pricePerNight(hotel.getPricePerNight())
                .availableRooms(hotel.getAvailableRooms())
                .build();
    }

    public Hotel toEntity(HotelRequest request) {
        return Hotel.builder()
                .name(request.getName())
                .address(request.getAddress())
                .city(request.getCity())
                .stars(request.getStars())
                .pricePerNight(request.getPricePerNight())
                .availableRooms(request.getAvailableRooms())
                .build();
    }
}

