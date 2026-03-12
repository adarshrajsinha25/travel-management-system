package com.easetravel.notification.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data @Builder
public class NotificationResponse {
    private Long id;
    private Long userId;
    private String recipientEmail;
    private String subject;
    private String type;
    private boolean success;
    private LocalDateTime sentAt;
}

