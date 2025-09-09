package com.EaseTravel.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
