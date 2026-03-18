package com.easetravel.notification.service;

import com.easetravel.notification.dto.request.EmailNotificationRequest;
import com.easetravel.notification.dto.response.NotificationResponse;

import java.util.List;

public interface NotificationService {
    NotificationResponse sendNotification(EmailNotificationRequest request);
    List<NotificationResponse> getNotificationsByUser(Long userId);
    List<NotificationResponse> getAllNotifications();
}

