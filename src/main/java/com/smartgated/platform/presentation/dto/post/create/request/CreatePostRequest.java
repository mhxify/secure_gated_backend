package com.smartgated.platform.presentation.dto.post.create.request;

import java.util.UUID;

public class CreatePostRequest {

    private String content;
    private String imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
