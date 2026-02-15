package com.smartgated.platform.infrastructure.repository.comment;

import com.smartgated.platform.domain.model.comments.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment , UUID> {

    List<Comment> findByPost_PostId(UUID postId);

}
