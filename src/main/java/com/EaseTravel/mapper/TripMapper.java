package com.EaseTravel.mapper;

import org.springframework.stereotype.Component;
import com.EaseTravel.model.entity.Trip;
import com.EaseTravel.model.dto.response.TripResponse;

@Component
public class TripMapper {
    public TripResponse toResponse(Trip trip) {
        TripResponse response = new TripResponse();
        // Map fields from trip to response here
        return response;
    }
}
