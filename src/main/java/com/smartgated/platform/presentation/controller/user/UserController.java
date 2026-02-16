package com.smartgated.platform.presentation.controller.user;

import java.util.List;
import java.util.UUID;

import com.smartgated.platform.presentation.dto.fcm.update.UpdateFcmToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartgated.platform.application.usecase.user.UserUseCase;
import com.smartgated.platform.domain.enums.user.UserRole;
import com.smartgated.platform.presentation.dto.user.edit.password.request.EditPasswordRequest;
import com.smartgated.platform.presentation.dto.user.edit.profile.request.EditProfileRequest;
import com.smartgated.platform.presentation.dto.user.register.register.RegisterRequest;
import com.smartgated.platform.presentation.dto.user.register.response.RegisterResponse;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserUseCase userUseCase;
    public UserController(UserUseCase userUseCase) { this.userUseCase = userUseCase; }


    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        RegisterResponse response = userUseCase.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<RegisterResponse>> getAllUsers() {
        return ResponseEntity.ok(userUseCase.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<RegisterResponse> getUserById(@PathVariable UUID userId) {
        return ResponseEntity.ok(userUseCase.getUserById(userId));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Void> updateUser(
            @PathVariable UUID userId,
            @RequestBody EditProfileRequest request
    ) {
        userUseCase.updateUser(userId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userUseCase.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPasswordOtp(@RequestParam String email) {
        String otp = userUseCase.forgotPasswordOtp(email);
        return ResponseEntity.ok(otp);
    }

    @PatchMapping("/edit-password")
    public ResponseEntity<String> editPassword(@RequestBody EditPasswordRequest request) {
        return ResponseEntity.ok(userUseCase.editPassword(request));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countUsers(@RequestParam(required = false) UserRole role) {
        return ResponseEntity.ok(userUseCase.countByRole(role));
    }

    @PatchMapping
    public ResponseEntity<Void> updateFcmToken(
            UUID userId ,
            UpdateFcmToken request
    ) {
        userUseCase.updateFcmToken(userId , request);
        return ResponseEntity.noContent().build();
    }

}