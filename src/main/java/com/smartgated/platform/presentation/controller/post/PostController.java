package com.smartgated.platform.presentation.controller.post;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartgated.platform.application.usecase.post.PostUseCase;
import com.smartgated.platform.presentation.dto.post.create.request.CreatePostRequest;
import com.smartgated.platform.presentation.dto.post.create.response.CreatePostResponse;
import com.smartgated.platform.presentation.dto.post.get.GetPost;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;



@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostUseCase postUseCase;

    public PostController(PostUseCase postUseCase) {
        this.postUseCase = postUseCase;
    }

    @PostMapping
    public ResponseEntity<CreatePostResponse> createPost(
        @RequestBody CreatePostRequest request
    ){
        CreatePostResponse response = postUseCase.createPost(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID postId) {
        postUseCase.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable UUID postId , @RequestBody CreatePostRequest request) {
        postUseCase.updatePost(postId, request);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<List<GetPost>> getPostsByUserId(@PathVariable UUID userId) {
        List<GetPost> posts = postUseCase.getPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("all")
    public ResponseEntity<List<GetPost>> getAllPosts() {
        List<GetPost> posts = postUseCase.getAllPosts();
        return ResponseEntity.ok(posts);
    }
}
