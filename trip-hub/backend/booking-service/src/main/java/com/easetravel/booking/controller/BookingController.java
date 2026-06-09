package com.easetravel.booking.controller;

import com.easetravel.booking.dto.request.BookingRequest;
import com.easetravel.booking.dto.response.ApiResponse;
import com.easetravel.booking.dto.response.BookingResponse;
import com.easetravel.booking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
@Tag(name = "Bookings", description = "Booking management endpoints")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @Operation(summary = "Create a new booking")
    public ResponseEntity<ApiResponse<BookingResponse>> createBooking(@Valid @RequestBody BookingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<BookingResponse>builder().success(true)
                        .message("Booking created").data(bookingService.createBooking(request)).build());
    }

    @GetMapping
    @Operation(summary = "Get all bookings")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getAllBookings() {
        return ResponseEntity.ok(ApiResponse.<List<BookingResponse>>builder().success(true)
                .message("Bookings retrieved").data(bookingService.getAllBookings()).build());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get booking by ID")
    public ResponseEntity<ApiResponse<BookingResponse>> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<BookingResponse>builder().success(true)
                .message("Booking retrieved").data(bookingService.getBookingById(id)).build());
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get bookings by user ID")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getBookingsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.<List<BookingResponse>>builder().success(true)
                .message("User bookings retrieved").data(bookingService.getBookingsByUser(userId)).build());
    }

    @PutMapping("/{id}/confirm")
    @Operation(summary = "Confirm a booking (called by payment-service)")
    public ResponseEntity<ApiResponse<BookingResponse>> confirmBooking(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<BookingResponse>builder().success(true)
                .message("Booking confirmed").data(bookingService.confirmBooking(id)).build());
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "Cancel a booking")
    public ResponseEntity<ApiResponse<BookingResponse>> cancelBooking(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<BookingResponse>builder().success(true)
                .message("Booking cancelled").data(bookingService.cancelBooking(id)).build());
    }
}

