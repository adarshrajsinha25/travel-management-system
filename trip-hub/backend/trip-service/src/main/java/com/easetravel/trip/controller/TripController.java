package com.easetravel.trip.controller;

import com.easetravel.trip.dto.request.TripRequest;
import com.easetravel.trip.dto.response.ApiResponse;
import com.easetravel.trip.dto.response.TripResponse;
import com.easetravel.trip.service.TripService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trips")
@RequiredArgsConstructor
@Tag(name = "Trips", description = "Trip management endpoints")
public class TripController {

    private final TripService tripService;

    @PostMapping
    @Operation(summary = "Create a new trip")
    public ResponseEntity<ApiResponse<TripResponse>> createTrip(@Valid @RequestBody TripRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<TripResponse>builder().success(true)
                        .message("Trip created").data(tripService.createTrip(request)).build());
    }

    @GetMapping
    @Operation(summary = "Get all trips")
    public ResponseEntity<ApiResponse<List<TripResponse>>> getAllTrips() {
        return ResponseEntity.ok(ApiResponse.<List<TripResponse>>builder().success(true)
                .message("Trips retrieved").data(tripService.getAllTrips()).build());
    }

    @GetMapping("/available")
    @Operation(summary = "Get available trips")
    public ResponseEntity<ApiResponse<List<TripResponse>>> getAvailableTrips() {
        return ResponseEntity.ok(ApiResponse.<List<TripResponse>>builder().success(true)
                .message("Available trips retrieved").data(tripService.getAvailableTrips()).build());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get trip by ID")
    public ResponseEntity<ApiResponse<TripResponse>> getTripById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<TripResponse>builder().success(true)
                .message("Trip retrieved").data(tripService.getTripById(id)).build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update trip")
    public ResponseEntity<ApiResponse<TripResponse>> updateTrip(
            @PathVariable Long id, @Valid @RequestBody TripRequest request) {
        return ResponseEntity.ok(ApiResponse.<TripResponse>builder().success(true)
                .message("Trip updated").data(tripService.updateTrip(id, request)).build());
    }

    @PutMapping("/{id}/decrement-seats")
    @Operation(summary = "Decrement available seats (called by booking-service)")
    public ResponseEntity<ApiResponse<TripResponse>> decrementSeats(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") int count) {
        return ResponseEntity.ok(ApiResponse.<TripResponse>builder().success(true)
                .message("Seats decremented").data(tripService.decrementSeats(id, count)).build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete trip")
    public ResponseEntity<ApiResponse<Void>> deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder().success(true).message("Trip deleted").build());
    }
}

