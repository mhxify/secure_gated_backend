package com.smartgated.platform.presentation.controller.notification;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartgated.platform.application.usecase.notification.NotificationUseCase;
import com.smartgated.platform.domain.model.notification.Notification;
import com.smartgated.platform.presentation.dto.notification.get.GetNotificationDto;
import com.smartgated.platform.presentation.dto.notification.post.request.PostNotificationRequest;
import com.smartgated.platform.presentation.dto.notification.post.response.PostNotificationResponse;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationUseCase notificationUseCase;

    public NotificationController(NotificationUseCase notificationUseCase) {
        this.notificationUseCase = notificationUseCase;
    }

    @PostMapping
    public ResponseEntity<PostNotificationResponse> postNotification(
            @RequestBody PostNotificationRequest request) {

        PostNotificationResponse response =
                notificationUseCase.postNotification(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<GetNotificationDto>> getNotificationsByUserId(
            @PathVariable UUID userId) {

        List<GetNotificationDto> notifications =
                notificationUseCase.getNotificationsByUserId(userId);

        return ResponseEntity.ok(notifications);
    }

    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<Void> patchNotificationReadStatus(
            @PathVariable UUID notificationId,
            @RequestParam boolean isRead) {

        notificationUseCase.patchNotificationReadStatus(notificationId, isRead);

        return ResponseEntity.noContent().build();
    }
}

