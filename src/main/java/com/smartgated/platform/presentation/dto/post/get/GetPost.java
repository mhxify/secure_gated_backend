package com.smartgated.platform.presentation.dto.post.get;

import com.smartgated.platform.presentation.dto.comment.get.GetComment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class GetPost {
    
    private UUID postId;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;
    private List<GetComment> comments;

    public List<GetComment> getComments() {
        return comments;
    }

    public void setComments(List<GetComment> comments) {
        this.comments = comments;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
