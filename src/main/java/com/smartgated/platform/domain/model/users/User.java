package com.smartgated.platform.domain.model.users;

import java.util.List;
import java.util.UUID;

import com.smartgated.platform.domain.enums.user.UserRole;
import com.smartgated.platform.domain.model.posts.Post;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID userId;
    private String fullname;
    private String email;
    private String password;
    private String imageUrl;
    private UserRole role;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    public User() {
    }

    public User(String fullname, String email, String password, String imageUrl, UserRole role) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.imageUrl = imageUrl;
        this.role = role;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }


}
