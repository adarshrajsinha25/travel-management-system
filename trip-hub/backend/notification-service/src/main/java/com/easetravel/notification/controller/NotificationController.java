package com.easetravel.notification.controller;

import com.easetravel.notification.dto.request.EmailNotificationRequest;
import com.easetravel.notification.dto.response.ApiResponse;
import com.easetravel.notification.dto.response.NotificationResponse;
import com.easetravel.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "Email notification endpoints")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    @Operation(summary = "Send an email notification")
    public ResponseEntity<ApiResponse<NotificationResponse>> sendNotification(
            @Valid @RequestBody EmailNotificationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<NotificationResponse>builder().success(true)
                        .message("Notification sent").data(notificationService.sendNotification(request)).build());
    }

    @GetMapping
    @Operation(summary = "Get all notifications")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getAllNotifications() {
        return ResponseEntity.ok(ApiResponse.<List<NotificationResponse>>builder().success(true)
                .message("Notifications retrieved").data(notificationService.getAllNotifications()).build());
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get notifications by user ID")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.<List<NotificationResponse>>builder().success(true)
                .message("User notifications retrieved")
                .data(notificationService.getNotificationsByUser(userId)).build());
    }
}

