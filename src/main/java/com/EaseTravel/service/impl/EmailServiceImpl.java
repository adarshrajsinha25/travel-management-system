package com.EaseTravel.service.impl;

import com.EaseTravel.service.EmailService;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Override
    public void sendEmail(String to, String subject, String body) {
        // Implement email sending logic here
    }
}
