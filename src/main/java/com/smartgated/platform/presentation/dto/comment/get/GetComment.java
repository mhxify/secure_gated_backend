package com.smartgated.platform.presentation.dto.comment.get;

import java.time.LocalDateTime;
import java.util.UUID;

public class GetComment {

    private UUID commentId;
    private String content;
    private UUID userId;
    private LocalDateTime createdAt;

    public UUID getCommentId(){
        return commentId;
    }

    public void setCommentId(UUID commentId){
        this.commentId = commentId;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public UUID getUserId(){
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


}
