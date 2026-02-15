package com.smartgated.platform.presentation.dto.comment.create.request;

import java.util.UUID;

public class CreateCommentRequest {

    private UUID postId;
    private String content ;
    private UUID userId ;

    public void setContent(String content) {
        this.content = content;
    }


    public String getContent() {
        return content;
    }

    public UUID getPostId() {
        return postId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
