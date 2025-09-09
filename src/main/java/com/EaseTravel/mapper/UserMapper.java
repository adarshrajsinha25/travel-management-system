package com.EaseTravel.mapper;

import org.springframework.stereotype.Component;
import com.EaseTravel.model.entity.User;
import com.EaseTravel.model.dto.response.AuthResponse;

@Component
public class UserMapper {
    public AuthResponse toAuthResponse(User user) {
        AuthResponse response = new AuthResponse();
        // Map fields from user to response here
        return response;
    }
}
