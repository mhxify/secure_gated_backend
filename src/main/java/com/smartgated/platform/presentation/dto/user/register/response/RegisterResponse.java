package com.smartgated.platform.presentation.dto.user.register.response;

import java.util.UUID;
import com.smartgated.platform.domain.enums.user.UserRole;

public class RegisterResponse {

    private UUID userId;
    private String fullname;
    private String email;
    private String imageUrl;
    private UserRole role;

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
}
