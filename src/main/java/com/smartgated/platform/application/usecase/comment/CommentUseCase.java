package com.smartgated.platform.application.usecase.comment;

import com.smartgated.platform.presentation.dto.comment.create.request.CreateCommentRequest;
import com.smartgated.platform.presentation.dto.comment.create.response.CreateCommentResponse;
import com.smartgated.platform.presentation.dto.comment.delete.DeleteCommentRequest;
import com.smartgated.platform.presentation.dto.comment.get.GetComment;

import java.util.List;
import java.util.UUID;

public interface CommentUseCase {

    CreateCommentResponse createComment(CreateCommentRequest request);

    List<GetComment> getPostComment(UUID postId);

    void deleteComment(DeleteCommentRequest request);

}
