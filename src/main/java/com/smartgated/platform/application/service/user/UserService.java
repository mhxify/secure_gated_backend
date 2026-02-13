package com.smartgated.platform.application.service.user;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartgated.platform.application.usecase.user.UserUseCase;
import com.smartgated.platform.domain.enums.user.UserRole;
import com.smartgated.platform.domain.model.users.User;
import com.smartgated.platform.infrastructure.repository.user.UserRepository;
import com.smartgated.platform.presentation.dto.user.edit.password.request.EditPasswordRequest;
import com.smartgated.platform.presentation.dto.user.edit.profile.request.EditProfileRequest;
import com.smartgated.platform.presentation.dto.user.register.register.RegisterRequest;
import com.smartgated.platform.presentation.dto.user.register.response.RegisterResponse;

@Service
@Transactional
public class UserService implements UserUseCase {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void deleteUser(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found: " + userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email already used: " + registerRequest.getEmail());
        }

        User user = new User();
        user.setFullname(registerRequest.getFullname());
        user.setEmail(registerRequest.getEmail());
        user.setRole(registerRequest.getRole());

        user.setPassword(registerRequest.getPassword());

        User saved = userRepository.save(user);

        RegisterResponse response = new RegisterResponse();
        response.setUserId(saved.getUserId());
        response.setFullname(saved.getFullname());
        response.setEmail(saved.getEmail());
        response.setRole(saved.getRole());
        response.setImageUrl(saved.getImageUrl());

        return response;
    }

    @Override
    public String forgotPasswordOtp(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        String otp = String.valueOf((int) (100000 + Math.random() * 900000));

        userRepository.save(user);

        return otp;
    }

    @Override
    public void updateUser(UUID userId, EditProfileRequest editProfileRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        user.setEmail(editProfileRequest.getEmail());
        //user.setImageUrl(editProfileRequest.getImageUrl());

        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public RegisterResponse getUserById(UUID userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    RegisterResponse response = new RegisterResponse();
                    response.setUserId(user.getUserId());
                    response.setFullname(user.getFullname());
                    response.setEmail(user.getEmail());
                    response.setRole(user.getRole());
                    response.setImageUrl(user.getImageUrl());
                    return response;
                })
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegisterResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> {
                    RegisterResponse response = new RegisterResponse();
                    response.setUserId(user.getUserId());
                    response.setFullname(user.getFullname());
                    response.setEmail(user.getEmail());
                    response.setRole(user.getRole());
                    response.setImageUrl(user.getImageUrl());
                    return response;
                })
                .toList();
    }

    @Override
    public String editPassword(EditPasswordRequest editPasswordRequest) {
        User user = userRepository.findById(editPasswordRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found: " + editPasswordRequest.getUserId()));

        if (!editPasswordRequest.getOldPassword().equals(user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(editPasswordRequest.getNewPassword());
        userRepository.save(user);

        return "Password updated successfully";
    }

    
    @Override
    @Transactional(readOnly = true)
    public long countByRole(UserRole role) {
        if (role == null) {
            return userRepository.count();
        }
        return userRepository.countByRole(role);
    }

}
