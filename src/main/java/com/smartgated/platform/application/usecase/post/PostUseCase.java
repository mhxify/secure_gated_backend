package com.smartgated.platform.application.usecase.post;

import java.util.List;
import java.util.UUID;

import com.smartgated.platform.presentation.dto.post.create.request.CreatePostRequest;
import com.smartgated.platform.presentation.dto.post.create.response.CreatePostResponse;
import com.smartgated.platform.presentation.dto.post.get.GetPost;

public interface PostUseCase {

    CreatePostResponse createPost(CreatePostRequest request);

    void deletePost(UUID postId);

    void updatePost(UUID postId, CreatePostRequest request);

    List<GetPost> getPostsByUserId(UUID userId);

    List<GetPost> getAllPosts();

    
}
