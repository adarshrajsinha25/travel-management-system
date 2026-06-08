package com.easetravel.booking.client;

import com.easetravel.booking.dto.response.ApiResponse;
import com.easetravel.booking.dto.response.TripResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "trip-service")
public interface TripServiceClient {

    @GetMapping("/api/v1/trips/{id}")
    ApiResponse<TripResponse> getTripById(@PathVariable("id") Long id);

    @PutMapping("/api/v1/trips/{id}/decrement-seats")
    ApiResponse<TripResponse> decrementSeats(@PathVariable("id") Long id,
                                              @RequestParam("count") int count);
}

