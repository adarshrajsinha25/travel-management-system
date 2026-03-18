package com.easetravel.trip.service;

import com.easetravel.trip.dto.request.HotelRequest;
import com.easetravel.trip.dto.response.HotelResponse;

import java.util.List;

public interface HotelService {
    HotelResponse createHotel(HotelRequest request);
    HotelResponse getHotelById(Long id);
    List<HotelResponse> getAllHotels();
    List<HotelResponse> searchByCity(String city);
    HotelResponse updateHotel(Long id, HotelRequest request);
    void deleteHotel(Long id);
}

