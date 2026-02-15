package com.smartgated.platform.presentation.controller.comment;

import java.util.List;
import java.util.UUID;

import com.smartgated.platform.presentation.dto.comment.create.response.CreateCommentResponse;
import com.smartgated.platform.presentation.dto.comment.delete.DeleteCommentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartgated.platform.application.usecase.comment.CommentUseCase;
import com.smartgated.platform.presentation.dto.comment.create.request.CreateCommentRequest;
import com.smartgated.platform.presentation.dto.comment.get.GetComment;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentUseCase commentUseCase;

    public CommentController(CommentUseCase commentUseCase) {
        this.commentUseCase = commentUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateCommentResponse> createComment(
            @RequestBody CreateCommentRequest request) {

        CreateCommentResponse response = commentUseCase.createComment(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<GetComment>> getCommentsByPost(
            @PathVariable UUID postId) {

        List<GetComment> comments = commentUseCase.getPostComment(postId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @RequestBody DeleteCommentRequest request) {

        commentUseCase.deleteComment(request);
        return ResponseEntity.noContent().build();
    }
}
