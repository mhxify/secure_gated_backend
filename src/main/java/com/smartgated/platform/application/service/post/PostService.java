package com.smartgated.platform.application.service.post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.smartgated.platform.application.usecase.post.PostUseCase;
import com.smartgated.platform.domain.model.posts.Post;
import com.smartgated.platform.domain.model.users.User;
import com.smartgated.platform.infrastructure.repository.post.PostRepository;
import com.smartgated.platform.infrastructure.repository.user.UserRepository;
import com.smartgated.platform.presentation.dto.post.create.request.CreatePostRequest;
import com.smartgated.platform.presentation.dto.post.create.response.CreatePostResponse;
import com.smartgated.platform.presentation.dto.post.get.GetPost;
import com.smartgated.platform.presentation.dto.post.update.UpdatePost;

@Service
public class PostService implements PostUseCase {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
    

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
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        postRepository.delete(post);        
    }

    @Override
    public void updatePost(UUID postId, UpdatePost request) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setContent(request.getContent());
        post.setImageUrl(request.getImageUrl());
        postRepository.save(post);
    }

    @Override
    public List<GetPost> getPostsByUserId(UUID userId) {
        List<Post> posts = postRepository.findByUserUserId(userId);
        return posts.stream().map(post -> {
            GetPost getPost = new GetPost();
            getPost.setPostId(post.getPostId());
            getPost.setContent(post.getContent());
            getPost.setImageUrl(post.getImageUrl());
            getPost.setCreatedAt(post.getCreatedAt());
            return getPost;
        }).toList();
    }


    @Override
    public List<GetPost> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> {
            GetPost getPost = new GetPost();
            getPost.setPostId(post.getPostId());
            getPost.setContent(post.getContent());
            getPost.setImageUrl(post.getImageUrl());
            getPost.setCreatedAt(post.getCreatedAt());
            return getPost;
        }).toList();
    }

    @Override
    public GetPost getPostById(UUID postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found"));
        GetPost getPost = new GetPost();
        getPost.setPostId(post.getPostId());
        getPost.setContent(post.getContent());
        getPost.setImageUrl(post.getImageUrl());
        getPost.setCreatedAt(post.getCreatedAt());
        return getPost;
    }
}
