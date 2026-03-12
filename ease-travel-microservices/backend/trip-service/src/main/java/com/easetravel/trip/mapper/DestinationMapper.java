package com.easetravel.trip.mapper;

import com.easetravel.trip.dto.request.DestinationRequest;
import com.easetravel.trip.dto.response.DestinationResponse;
import com.easetravel.trip.entity.Destination;
import org.springframework.stereotype.Component;

@Component
public class DestinationMapper {
    public DestinationResponse toResponse(Destination destination) {
        return DestinationResponse.builder()
                .id(destination.getId())
                .name(destination.getName())
                .country(destination.getCountry())
                .description(destination.getDescription())
                .imageUrl(destination.getImageUrl())
                .build();
    }

    public Destination toEntity(DestinationRequest request) {
        return Destination.builder()
                .name(request.getName())
                .country(request.getCountry())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .build();
    }
}

