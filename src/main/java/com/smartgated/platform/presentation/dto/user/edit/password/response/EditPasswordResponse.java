package com.smartgated.platform.presentation.dto.user.edit.password.response;

import java.util.UUID;

import com.smartgated.platform.domain.enums.user.UserRole;

public class EditPasswordResponse {
    
    private UUID userId;
    private String fullname;
    private String email;
    private String imageUrl;
    private UserRole role;

    public UUID getUserId() {
        return userId;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserRole getRole() {
        return role;
    }
    
}
