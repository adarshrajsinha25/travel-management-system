package com.EaseTravel.travel_management_system.service.impl;

import com.EaseTravel.travel_management_system.service.EmailService;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Override
    public void sendEmail(String to, String subject, String body) {
        // Implement email sending logic here
    }
}
