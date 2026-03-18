package com.easetravel.trip.controller;

import com.easetravel.trip.dto.request.FlightRequest;
import com.easetravel.trip.dto.response.ApiResponse;
import com.easetravel.trip.dto.response.FlightResponse;
import com.easetravel.trip.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/flights")
@RequiredArgsConstructor
@Tag(name = "Flights", description = "Flight management endpoints")
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    public ResponseEntity<ApiResponse<FlightResponse>> createFlight(@Valid @RequestBody FlightRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<FlightResponse>builder().success(true)
                        .message("Flight created").data(flightService.createFlight(request)).build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FlightResponse>>> getAllFlights() {
        return ResponseEntity.ok(ApiResponse.<List<FlightResponse>>builder().success(true)
                .message("Flights retrieved").data(flightService.getAllFlights()).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FlightResponse>> getFlightById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<FlightResponse>builder().success(true)
                .message("Flight retrieved").data(flightService.getFlightById(id)).build());
    }

    @GetMapping("/search")
    @Operation(summary = "Search flights by origin and destination")
    public ResponseEntity<ApiResponse<List<FlightResponse>>> searchFlights(
            @RequestParam String origin,
            @RequestParam String destination) {
        return ResponseEntity.ok(ApiResponse.<List<FlightResponse>>builder().success(true)
                .message("Flights found").data(flightService.searchFlights(origin, destination)).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FlightResponse>> updateFlight(
            @PathVariable Long id, @Valid @RequestBody FlightRequest request) {
        return ResponseEntity.ok(ApiResponse.<FlightResponse>builder().success(true)
                .message("Flight updated").data(flightService.updateFlight(id, request)).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder().success(true).message("Flight deleted").build());
    }
}

