package com.smartgated.platform.infrastructure.repository.notification;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartgated.platform.domain.model.notification.Notification;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    List<Notification> findByUserUserId(UUID userId);

    
}
