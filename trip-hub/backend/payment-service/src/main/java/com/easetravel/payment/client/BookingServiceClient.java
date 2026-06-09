package com.easetravel.payment.client;

import com.easetravel.payment.dto.response.ApiResponse;
import com.easetravel.payment.dto.response.BookingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "booking-service")
public interface BookingServiceClient {

    @GetMapping("/api/v1/bookings/{id}")
    ApiResponse<BookingResponse> getBookingById(@PathVariable("id") Long id);

    @PutMapping("/api/v1/bookings/{id}/confirm")
    ApiResponse<BookingResponse> confirmBooking(@PathVariable("id") Long id);
}

