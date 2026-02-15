package com.smartgated.platform.application.service.comment;

import com.smartgated.platform.application.usecase.comment.CommentUseCase;
import com.smartgated.platform.domain.model.comments.Comment;
import com.smartgated.platform.domain.model.posts.Post;
import com.smartgated.platform.domain.model.users.User;
import com.smartgated.platform.infrastructure.repository.comment.CommentRepository;
import com.smartgated.platform.infrastructure.repository.post.PostRepository;
import com.smartgated.platform.infrastructure.repository.user.UserRepository;
import com.smartgated.platform.presentation.dto.comment.create.request.CreateCommentRequest;
import com.smartgated.platform.presentation.dto.comment.create.response.CreateCommentResponse;
import com.smartgated.platform.presentation.dto.comment.delete.DeleteCommentRequest;
import com.smartgated.platform.presentation.dto.comment.get.GetComment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CommentService implements CommentUseCase {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentService(
            CommentRepository commentRepository ,
            UserRepository userRepository ,
            PostRepository postRepository
    ) {
        this.commentRepository = commentRepository ;
        this.postRepository = postRepository ;
        this.userRepository = userRepository ;
    }


    @Override
    public CreateCommentResponse createComment(CreateCommentRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = new Comment();

        comment.setContent(request.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUserId(user.getUserId());
        comment.setPostId(post.getPostId());

        Comment createdComment = commentRepository.save(comment);

        CreateCommentResponse response = new CreateCommentResponse();

        response.setPostId(createdComment.getPostId());

        return response ;

    }

    @Override
    public List<GetComment> getPostComment(UUID postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new  RuntimeException("Post not found"));

        List<Comment> comments = commentRepository.findByPost_PostId(postId);

        List<GetComment> postComments = comments.stream()
                .map(comment -> {
                    GetComment dto = new GetComment();
                    dto.setCommentId(comment.getCommentId());
                    dto.setContent(comment.getContent());
                    dto.setCreatedAt(comment.getCreatedAt());
                    dto.setUserId(comment.getUserId());
                    return dto;
                })
                .toList();

        return postComments;

    }

    @Override
    public void deleteComment (DeleteCommentRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User Not Found") );

        commentRepository.deleteById(request.getCommentId());
    }

}
