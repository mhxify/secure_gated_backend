package com.smartgated.platform.presentation.dto.comment.delete;

import java.util.UUID;

public class DeleteCommentRequest {

    private UUID commentId ;

    private UUID userId;

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getCommentId() {
        return commentId;
    }

    public void setCommentId(UUID commentId) {
        this.commentId = commentId;
    }

}
