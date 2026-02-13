package com.smartgated.platform.application.service.notification;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.smartgated.platform.application.usecase.notification.NotificationUseCase;
import com.smartgated.platform.domain.model.notification.Notification;
import com.smartgated.platform.infrastructure.repository.notification.NotificationRepository;
import com.smartgated.platform.infrastructure.repository.user.UserRepository;
import com.smartgated.platform.presentation.dto.notification.get.GetNotificationDto;
import com.smartgated.platform.presentation.dto.notification.post.request.PostNotificationRequest;
import com.smartgated.platform.presentation.dto.notification.post.response.PostNotificationResponse;

@Service
public class NotificationService implements NotificationUseCase {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository,
                               UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PostNotificationResponse postNotification(PostNotificationRequest request) {

        Notification notification = new Notification();
        notification.setContent(request.getContent());
        notification.setRead(false);
        notification.setCreatedAt(request.getCreatedAt());

        var user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        notification.setUser(user);

        Notification savedNotification = notificationRepository.save(notification);

        PostNotificationResponse response = new PostNotificationResponse();
        response.setNotificationId(savedNotification.getNotificationId());
        response.setContent(savedNotification.getContent());
        response.setRead(savedNotification.isRead());
        response.setCreatedAt(savedNotification.getCreatedAt());

        return response;
    }

    @Override
    public List<GetNotificationDto> getNotificationsByUserId(UUID userId) {

        List<Notification> notifications =
                notificationRepository.findByUserUserId(userId);

        return notifications.stream()
                .map(notification -> {
                    GetNotificationDto dto = new GetNotificationDto();
                    dto.setNotificationId(notification.getNotificationId());
                    dto.setContent(notification.getContent());
                    dto.setRead(notification.isRead());
                    dto.setCreatedAt(notification.getCreatedAt());
                    return dto;
                })
                .toList();
    }


    @Override
    public void patchNotificationReadStatus(UUID notificationId, boolean isRead) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setRead(isRead);
        notificationRepository.save(notification);
    }
}
