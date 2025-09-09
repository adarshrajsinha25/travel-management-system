package com.EaseTravel.travel_management_system.mapper;

import org.springframework.stereotype.Component;
import com.EaseTravel.travel_management_system.model.entity.Trip;
import com.EaseTravel.travel_management_system.model.dto.response.TripResponse;

@Component
public class TripMapper {
    public TripResponse toResponse(Trip trip) {
        TripResponse response = new TripResponse();
        // Map fields from trip to response here
        return response;
    }
}
