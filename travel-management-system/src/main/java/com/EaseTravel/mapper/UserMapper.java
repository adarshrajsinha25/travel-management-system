package com.EaseTravel.travel_management_system.mapper;

import org.springframework.stereotype.Component;
import com.EaseTravel.travel_management_system.model.entity.User;
import com.EaseTravel.travel_management_system.model.dto.response.AuthResponse;

@Component
public class UserMapper {
    public AuthResponse toAuthResponse(User user) {
        AuthResponse response = new AuthResponse();
        // Map fields from user to response here
        return response;
    }
}
