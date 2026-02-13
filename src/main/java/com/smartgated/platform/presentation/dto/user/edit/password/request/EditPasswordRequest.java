package com.smartgated.platform.presentation.dto.user.edit.password.request;

import java.util.UUID;

public class EditPasswordRequest {

    private UUID userId;
    private String oldPassword;
    private String newPassword;

    public UUID getUserId() {
        return userId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
        
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
        
}
