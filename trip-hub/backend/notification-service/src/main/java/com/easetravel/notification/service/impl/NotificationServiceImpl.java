package com.easetravel.notification.service.impl;

import com.easetravel.notification.dto.request.EmailNotificationRequest;
import com.easetravel.notification.dto.response.NotificationResponse;
import com.easetravel.notification.entity.Notification;
import com.easetravel.notification.repository.NotificationRepository;
import com.easetravel.notification.service.EmailService;
import com.easetravel.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @Override
    public NotificationResponse sendNotification(EmailNotificationRequest request) {
        Notification notification = Notification.builder()
                .userId(request.getUserId())
                .recipientEmail(request.getRecipientEmail())
                .subject(request.getSubject())
                .body(request.getBody())
                .type(request.getType())
                .build();

        try {
            emailService.sendEmail(request.getRecipientEmail(), request.getSubject(), request.getBody());
            notification.setSuccess(true);
        } catch (Exception e) {
            log.warn("Email delivery failed for {}: {}", request.getRecipientEmail(), e.getMessage());
            notification.setSuccess(false);
        }

        return toResponse(notificationRepository.save(notification));
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotificationsByUser(Long userId) {
        return notificationRepository.findByUserId(userId).stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    private NotificationResponse toResponse(Notification n) {
        return NotificationResponse.builder()
                .id(n.getId()).userId(n.getUserId())
                .recipientEmail(n.getRecipientEmail()).subject(n.getSubject())
                .type(n.getType().name()).success(n.isSuccess()).sentAt(n.getSentAt())
                .build();
    }
}

