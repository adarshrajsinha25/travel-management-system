package com.easetravel.trip.service.impl;

import com.easetravel.trip.dto.request.HotelRequest;
import com.easetravel.trip.dto.response.HotelResponse;
import com.easetravel.trip.entity.Hotel;
import com.easetravel.trip.exception.ResourceNotFoundException;
import com.easetravel.trip.mapper.HotelMapper;
import com.easetravel.trip.repository.HotelRepository;
import com.easetravel.trip.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @Override
    public HotelResponse createHotel(HotelRequest request) {
        return hotelMapper.toResponse(hotelRepository.save(hotelMapper.toEntity(request)));
    }

    @Override
    @Transactional(readOnly = true)
    public HotelResponse getHotelById(Long id) {
        return hotelMapper.toResponse(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotelResponse> getAllHotels() {
        return hotelRepository.findAll().stream().map(hotelMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotelResponse> searchByCity(String city) {
        return hotelRepository.findByCityIgnoreCase(city).stream()
                .map(hotelMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public HotelResponse updateHotel(Long id, HotelRequest request) {
        Hotel hotel = findOrThrow(id);
        hotel.setName(request.getName());
        hotel.setAddress(request.getAddress());
        hotel.setCity(request.getCity());
        hotel.setStars(request.getStars());
        hotel.setPricePerNight(request.getPricePerNight());
        hotel.setAvailableRooms(request.getAvailableRooms());
        return hotelMapper.toResponse(hotelRepository.save(hotel));
    }

    @Override
    public void deleteHotel(Long id) {
        if (!hotelRepository.existsById(id)) throw new ResourceNotFoundException("Hotel not found with id: " + id);
        hotelRepository.deleteById(id);
    }

    private Hotel findOrThrow(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));
    }
}

