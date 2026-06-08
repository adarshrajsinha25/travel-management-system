package com.easetravel.notification.entity;

import com.easetravel.notification.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Column(nullable = false)
    private String recipientEmail;

    @Column(nullable = false)
    private String subject;

    @Column(length = 5000)
    private String body;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private NotificationType type = NotificationType.GENERAL;

    @Builder.Default
    private boolean success = false;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime sentAt;
}

