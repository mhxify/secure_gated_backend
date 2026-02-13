package com.smartgated.platform.application.usecase.notification;

import java.util.List;
import java.util.UUID;

import com.smartgated.platform.presentation.dto.notification.get.GetNotificationDto;
import com.smartgated.platform.presentation.dto.notification.post.request.PostNotificationRequest;
import com.smartgated.platform.presentation.dto.notification.post.response.PostNotificationResponse;

public interface NotificationUseCase {
    
    PostNotificationResponse postNotification(PostNotificationRequest request);

    void patchNotificationReadStatus(UUID notificationId, boolean isRead);

    List<GetNotificationDto> getNotificationsByUserId(UUID userId);
}
