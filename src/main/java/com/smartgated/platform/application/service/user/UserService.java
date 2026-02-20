package com.smartgated.platform.application.service.user;

import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

import com.smartgated.platform.application.service.email.EmailService;
import com.smartgated.platform.application.service.otp.OtpService;
import com.smartgated.platform.presentation.dto.fcm.update.UpdateFcmToken;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder ;
    private final OtpService otpService ;
    private final EmailService emailService ;

    public UserService(
            UserRepository userRepository ,
            PasswordEncoder passwordEncoder ,
            OtpService otpService ,
            EmailService emailService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder ;
        this.otpService = otpService ;
        this.emailService = emailService ;
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

        String tempPassword = generateTempPassword(10);

        User user = new User();
        user.setFullname(registerRequest.getFullname());
        user.setEmail(registerRequest.getEmail());
        user.setRole(registerRequest.getRole());
        user.setPassword(passwordEncoder.encode(tempPassword));

        User saved = userRepository.save(user);

        emailService.send(
                saved.getEmail(),
                "Your SmartGated Account",
                "Your account has been created.\n\n" +
                        "Email: " + saved.getEmail() + "\n" +
                        "Temporary Password: " + tempPassword + "\n\n" +
                        "Please login and change your password."
        );

        RegisterResponse response = new RegisterResponse();
        response.setUserId(saved.getUserId());
        response.setFullname(saved.getFullname());
        response.setEmail(saved.getEmail());
        response.setRole(saved.getRole());
        response.setImageUrl(saved.getImageUrl());
        return response;
    }

    private String generateTempPassword(int len) {
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789@#";
        SecureRandom r = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) sb.append(chars.charAt(r.nextInt(chars.length())));
        return sb.toString();
    }

    @Override
    public String forgotPasswordOtp(String email , String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user) ;
        return "Password is changed";
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
    public String editPassword(EditPasswordRequest req) {
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found: " + req.getUserId()));

        if (!passwordEncoder.matches(req.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
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

    @Override
    public void updateFcmToken(
            UUID userId ,
            UpdateFcmToken request
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not found"));

        user.setFcmToken(request.getFcmToken());

        userRepository.save(user);

    }



}
