package com.smartgated.platform.presentation.dto.comment.create.response;

import java.util.UUID;

public class CreateCommentResponse {
    private UUID postId ;

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public UUID getPostId() {
        return postId;
    }
}
