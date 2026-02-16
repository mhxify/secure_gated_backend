package com.smartgated.platform.presentation.dto.notification.post.request;

import java.time.LocalDateTime;
import java.util.UUID;


public class PostNotificationRequest {

    private String content;
    private UUID userId;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
}
