package com.smartgated.platform.presentation.controller.auth;

import com.smartgated.platform.application.service.jwt.JwtService;
import com.smartgated.platform.application.service.otp.OtpService;
import com.smartgated.platform.domain.model.users.User;
import com.smartgated.platform.infrastructure.repository.user.UserRepository;
import com.smartgated.platform.presentation.dto.auth.request.LoginRequest;
import com.smartgated.platform.presentation.dto.auth.response.LoginResponse;
import com.smartgated.platform.presentation.dto.auth.response.MeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final OtpService otpService;

    public AuthController(
            UserRepository userRepository,
            PasswordEncoder encoder,
            JwtService jwtService ,
            OtpService otpService
    ) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.otpService = otpService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        System.out.println("LOGIN ENDPOINT HIT");

        User user = userRepository.findByEmail(request.email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!encoder.matches(request.password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        LoginResponse response = new LoginResponse();
        response.token = jwtService.generateToken(user);
        return response;
    }

    @PostMapping("/send")
    public void send(@RequestParam String email) {
        otpService.sendOtp(email);
    }

    @PostMapping("/verify")
    public void verify(@RequestParam String email, @RequestParam String code) {
        otpService.verifyOtp(email, code);
    }

    @GetMapping("/me")
    public ResponseEntity<MeResponse> me() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof User user)) {
            return ResponseEntity.status(401).build();
        }

        MeResponse res = new MeResponse();
        res.setUserId(user.getUserId());
        res.setFullname(user.getFullname());
        res.setEmail(user.getEmail());
        res.setRole(user.getRole());
        res.setImageUrl(user.getImageUrl());

        return ResponseEntity.ok(res);
    }
}
