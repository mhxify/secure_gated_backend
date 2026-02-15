package com.smartgated.platform.application.service.post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartgated.platform.application.usecase.post.PostUseCase;
import com.smartgated.platform.domain.model.posts.Post;
import com.smartgated.platform.domain.model.users.User;
import com.smartgated.platform.infrastructure.repository.post.PostRepository;
import com.smartgated.platform.infrastructure.repository.user.UserRepository;
import com.smartgated.platform.infrastructure.repository.comment.CommentRepository;
import com.smartgated.platform.domain.model.comments.Comment;
import com.smartgated.platform.presentation.dto.comment.get.GetComment;
import com.smartgated.platform.presentation.dto.post.create.request.CreatePostRequest;
import com.smartgated.platform.presentation.dto.post.create.response.CreatePostResponse;
import com.smartgated.platform.presentation.dto.post.get.GetPost;
import com.smartgated.platform.presentation.dto.post.update.UpdatePost;

@Service
@Transactional
public class PostService implements PostUseCase {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public PostService(
            PostRepository postRepository,
            UserRepository userRepository,
            CommentRepository commentRepository) {

        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    // ========================= CREATE =========================

    @Override
    public CreatePostResponse createPost(CreatePostRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setContent(request.getContent());
        post.setImageUrl(request.getImageUrl());
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());

        Post savedPost = postRepository.save(post);

        CreatePostResponse response = new CreatePostResponse();
        response.setPostId(savedPost.getPostId());
        response.setContent(savedPost.getContent());
        response.setImageUrl(savedPost.getImageUrl());
        response.setCreatedAt(savedPost.getCreatedAt());

        return response;
    }

    @Override
    public void deletePost(UUID postId) {

        if (!postRepository.existsById(postId)) {
            throw new RuntimeException("Post not found");
        }

        postRepository.deleteById(postId);
    }

    @Override
    public void updatePost(UUID postId, UpdatePost request) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setContent(request.getContent());
        post.setImageUrl(request.getImageUrl());
    }


    @Override
    public List<GetPost> getPostsByUserId(UUID userId) {

        return postRepository.findByUserUserId(userId)
                .stream()
                .map(this::mapToGetPost)
                .toList();
    }

    @Override
    public List<GetPost> getAllPosts() {

        return postRepository.findAll()
                .stream()
                .map(this::mapToGetPost)
                .toList();
    }

    @Override
    public GetPost getPostById(UUID postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return mapToGetPost(post);
    }


    private GetPost mapToGetPost(Post post) {

        GetPost dto = new GetPost();
        dto.setPostId(post.getPostId());
        dto.setContent(post.getContent());
        dto.setImageUrl(post.getImageUrl());
        dto.setCreatedAt(post.getCreatedAt());

        // 🔥 Fetch comments safely (no lazy issues)
        List<GetComment> comments = commentRepository
                .findByPost_PostId(post.getPostId())
                .stream()
                .map(this::mapToGetComment)
                .toList();

        dto.setComments(comments);

        return dto;
    }

    private GetComment mapToGetComment(Comment comment) {

        GetComment dto = new GetComment();
        dto.setCommentId(comment.getCommentId());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUserId(comment.getUserId());

        return dto;
    }
}
