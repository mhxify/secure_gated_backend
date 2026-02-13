package com.smartgated.platform.domain.model.comments;

import java.time.LocalDateTime;
import java.util.UUID;

import com.smartgated.platform.domain.model.posts.Post;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comments")
public class Comment {
    
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID commentId;
    private String content;
    private UUID postId;
    private UUID userId;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    public Comment() {
    }

    public Comment(String content, UUID postId, UUID userId, LocalDateTime createdAt) {
        this.content = content;
        this.postId = postId;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Post getPost() {
        return post;
    }

    
    public UUID getCommentId() {
        return commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
