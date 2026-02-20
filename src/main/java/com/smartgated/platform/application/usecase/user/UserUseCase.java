package com.smartgated.platform.application.usecase.user;

import java.util.List;
import java.util.UUID;

import com.smartgated.platform.domain.enums.user.UserRole;
import com.smartgated.platform.presentation.dto.fcm.update.UpdateFcmToken;
import com.smartgated.platform.presentation.dto.user.edit.password.request.EditPasswordRequest;
import com.smartgated.platform.presentation.dto.user.edit.profile.request.EditProfileRequest;
import com.smartgated.platform.presentation.dto.user.register.register.RegisterRequest;
import com.smartgated.platform.presentation.dto.user.register.response.*;;


public interface UserUseCase {
    
    void deleteUser(UUID userId);
    
    RegisterResponse register(RegisterRequest registerRequest);
    
    String forgotPasswordOtp(String email , String password);
    
    void updateUser(UUID userId, EditProfileRequest registerRequest);
    
    String editPassword(EditPasswordRequest editPasswordRequest);
    
    RegisterResponse getUserById(UUID userId);
    
    List<RegisterResponse> getAllUsers();
    
    long countByRole(UserRole role);

    void updateFcmToken(UUID userId , UpdateFcmToken request);

}

