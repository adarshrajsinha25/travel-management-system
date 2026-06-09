package com.easetravel.trip.controller;

import com.easetravel.trip.dto.request.DestinationRequest;
import com.easetravel.trip.dto.response.ApiResponse;
import com.easetravel.trip.dto.response.DestinationResponse;
import com.easetravel.trip.service.DestinationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/destinations")
@RequiredArgsConstructor
@Tag(name = "Destinations", description = "Destination management endpoints")
public class DestinationController {

    private final DestinationService destinationService;

    @PostMapping
    public ResponseEntity<ApiResponse<DestinationResponse>> create(@Valid @RequestBody DestinationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<DestinationResponse>builder().success(true)
                        .message("Destination created").data(destinationService.createDestination(request)).build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DestinationResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.<List<DestinationResponse>>builder().success(true)
                .message("Destinations retrieved").data(destinationService.getAllDestinations()).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DestinationResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<DestinationResponse>builder().success(true)
                .message("Destination retrieved").data(destinationService.getDestinationById(id)).build());
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<DestinationResponse>>> searchByCountry(@RequestParam String country) {
        return ResponseEntity.ok(ApiResponse.<List<DestinationResponse>>builder().success(true)
                .message("Destinations found").data(destinationService.findByCountry(country)).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DestinationResponse>> update(
            @PathVariable Long id, @Valid @RequestBody DestinationRequest request) {
        return ResponseEntity.ok(ApiResponse.<DestinationResponse>builder().success(true)
                .message("Destination updated").data(destinationService.updateDestination(id, request)).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        destinationService.deleteDestination(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder().success(true).message("Destination deleted").build());
    }
}

