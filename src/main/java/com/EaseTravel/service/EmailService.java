package com.EaseTravel.travel_management_system.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
