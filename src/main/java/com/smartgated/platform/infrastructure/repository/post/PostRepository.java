package com.smartgated.platform.infrastructure.repository.post;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartgated.platform.domain.model.posts.Post;

public interface PostRepository extends JpaRepository<Post,UUID>{

    List<Post> findByUserUserId(UUID userId);
    
}
