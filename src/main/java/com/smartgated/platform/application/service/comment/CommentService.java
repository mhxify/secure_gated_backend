package com.smartgated.platform.application.service.comment;

import com.smartgated.platform.application.usecase.comment.CommentUseCase;
import com.smartgated.platform.domain.model.comments.Comment;
import com.smartgated.platform.domain.model.notification.Notification;
import com.smartgated.platform.domain.model.posts.Post;
import com.smartgated.platform.domain.model.users.User;
import com.smartgated.platform.infrastructure.repository.comment.CommentRepository;
import com.smartgated.platform.infrastructure.repository.notification.NotificationRepository;
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
    private final NotificationRepository notificationRepository ;


    public CommentService(
            CommentRepository commentRepository ,
            UserRepository userRepository ,
            PostRepository postRepository ,
            NotificationRepository notificationRepository
    ) {
        this.commentRepository = commentRepository ;
        this.postRepository = postRepository ;
        this.userRepository = userRepository ;
        this.notificationRepository = notificationRepository ;
    }


    @Override
    public CreateCommentResponse createComment(CreateCommentRequest request) {
        User commenter = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUserId(commenter.getUserId());
        comment.setPostId(post.getPostId());

        Comment createdComment = commentRepository.save(comment);

        User postOwner = post.getUser();
        if (postOwner != null && !postOwner.getUserId().equals(commenter.getUserId())) {

            Notification notification = new Notification();
            notification.setCreatedAt(LocalDateTime.now());
            notification.setRead(false);
            notification.setUser(postOwner);

            String content = commenter.getFullname() + " commented on your post: \"" +
                    shorten(request.getContent(), 60) + "\"";
            notification.setContent(content);

            notificationRepository.save(notification);
        }

        CreateCommentResponse response = new CreateCommentResponse();

        response.setPostId(createdComment.getPostId());

        return response ;

    }

    private String shorten(String text, int max) {
        if (text == null) return "";
        text = text.trim();
        return text.length() <= max ? text : text.substring(0, max) + "...";
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
