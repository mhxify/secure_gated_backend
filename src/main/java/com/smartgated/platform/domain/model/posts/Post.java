package com.smartgated.platform.domain.model.posts;

import org.hibernate.grammars.hql.HqlParser.DateTimeContext;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts")
public class Post {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private String postId;
    private String content;
    private String imageUrl;
    private DateTimeContext createdAt;

    public Post() {
    }

    public Post(String content, String imageUrl) {
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public String getPostId() {
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

    public DateTimeContext getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTimeContext createdAt) {
        this.createdAt = createdAt;
    }

}
