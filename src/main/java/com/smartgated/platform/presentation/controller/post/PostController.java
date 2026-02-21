package com.smartgated.platform.presentation.controller.post;

import com.smartgated.platform.application.service.file.FileStorageService;
import org.springframework.web.bind.annotation.*;

import com.smartgated.platform.application.usecase.post.PostUseCase;
import com.smartgated.platform.presentation.dto.post.create.request.CreatePostRequest;
import com.smartgated.platform.presentation.dto.post.create.response.CreatePostResponse;
import com.smartgated.platform.presentation.dto.post.get.GetPost;
import com.smartgated.platform.presentation.dto.post.update.UpdatePost;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostUseCase postUseCase;
    private final FileStorageService fileStorageService ;

    public PostController(
            PostUseCase postUseCase ,
            FileStorageService fileStorageService
    ) {
        this.postUseCase = postUseCase;
        this.fileStorageService = fileStorageService ;
    }

    @PostMapping(value = "/create", consumes = "multipart/form-data")
    public ResponseEntity<CreatePostResponse> createPost(
            @RequestParam UUID userId,
            @RequestParam String content,
            @RequestPart(required = false) MultipartFile image
    ) {
        String imageUrl = fileStorageService.savePostImage(image);

        CreatePostRequest req = new CreatePostRequest();
        req.setUserId(userId);
        req.setContent(content);
        req.setImageUrl(imageUrl);

        return ResponseEntity.ok(postUseCase.createPost(req));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID postId) {
        postUseCase.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable UUID postId , @RequestBody UpdatePost request) {
        postUseCase.updatePost(postId, request);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{postId}")
    public ResponseEntity<GetPost> getPostById(@PathVariable UUID postId) {
        GetPost post = postUseCase.getPostById(postId);
        return ResponseEntity.ok(post);
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
