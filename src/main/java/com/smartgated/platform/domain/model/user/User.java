package com.smartgated.platform.domain.model.user;

import java.util.UUID;

import jakarta.persistence.*;
import com.smartgated.platform.domain.enums.user.UserRole;

@MappedSuperclass
public abstract class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;


    public User() {
    }

    public User(
        UUID userUuid , 
        String fullName, 
        String email, 
        String password,
        UserRole userRole
    ) {
        this.userId = userUuid;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    
}
