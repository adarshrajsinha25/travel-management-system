package com.easetravel.trip.controller;

import com.easetravel.trip.dto.request.HotelRequest;
import com.easetravel.trip.dto.response.ApiResponse;
import com.easetravel.trip.dto.response.HotelResponse;
import com.easetravel.trip.service.HotelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels")
@RequiredArgsConstructor
@Tag(name = "Hotels", description = "Hotel management endpoints")
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<ApiResponse<HotelResponse>> createHotel(@Valid @RequestBody HotelRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<HotelResponse>builder().success(true)
                        .message("Hotel created").data(hotelService.createHotel(request)).build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<HotelResponse>>> getAllHotels() {
        return ResponseEntity.ok(ApiResponse.<List<HotelResponse>>builder().success(true)
                .message("Hotels retrieved").data(hotelService.getAllHotels()).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HotelResponse>> getHotelById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<HotelResponse>builder().success(true)
                .message("Hotel retrieved").data(hotelService.getHotelById(id)).build());
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<HotelResponse>>> searchByCity(@RequestParam String city) {
        return ResponseEntity.ok(ApiResponse.<List<HotelResponse>>builder().success(true)
                .message("Hotels found").data(hotelService.searchByCity(city)).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<HotelResponse>> updateHotel(
            @PathVariable Long id, @Valid @RequestBody HotelRequest request) {
        return ResponseEntity.ok(ApiResponse.<HotelResponse>builder().success(true)
                .message("Hotel updated").data(hotelService.updateHotel(id, request)).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder().success(true).message("Hotel deleted").build());
    }
}

