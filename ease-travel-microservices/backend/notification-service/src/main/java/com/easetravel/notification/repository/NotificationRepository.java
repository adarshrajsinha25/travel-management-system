package com.easetravel.notification.repository;

import com.easetravel.notification.entity.Notification;
import com.easetravel.notification.enums.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
    List<Notification> findByType(NotificationType type);
    List<Notification> findBySuccess(boolean success);
}

