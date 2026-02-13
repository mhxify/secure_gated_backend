package com.smartgated.platform.domain.model.posts;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.grammars.hql.HqlParser.DateTimeContext;

import com.smartgated.platform.domain.model.comments.Comment;
import com.smartgated.platform.domain.model.users.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts")
public class Post {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID postId;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    
    public Post() {
    }

    public Post(String content, String imageUrl) {
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public UUID getPostId() {
        return postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    

}
