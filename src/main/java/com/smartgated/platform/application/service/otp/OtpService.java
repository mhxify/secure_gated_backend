package com.smartgated.platform.application.service.otp;

import com.smartgated.platform.application.service.email.EmailService;
import com.smartgated.platform.domain.model.otp.EmailOtp;
import com.smartgated.platform.infrastructure.repository.otp.EmailOtpRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class OtpService {

    private final EmailOtpRepository otpRepository;
    private final EmailService emailService;

    public OtpService(EmailOtpRepository otpRepository, EmailService emailService) {
        this.otpRepository = otpRepository;
        this.emailService = emailService;
    }

    public void sendOtp(String email) {
        String code = String.valueOf(100000 + new SecureRandom().nextInt(900000)); // 6 digits

        EmailOtp otp = new EmailOtp();
        otp.setEmail(email);
        otp.setCode(code);
        otp.setExpiresAt(LocalDateTime.now().plusMinutes(10));
        otp.setUsed(false);

        otpRepository.save(otp);

        emailService.send(
                email,
                "SmartGated OTP Code",
                "Your OTP code is: " + code + "\nExpires in 10 minutes."
        );
    }

    public void verifyOtp(String email, String code) {
        EmailOtp otp = otpRepository.findTopByEmailOrderByExpiresAtDesc(email)
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (otp.isUsed()) throw new RuntimeException("OTP already used");
        if (otp.getExpiresAt().isBefore(LocalDateTime.now())) throw new RuntimeException("OTP expired");
        if (!otp.getCode().equals(code)) throw new RuntimeException("Invalid OTP");

        otp.setUsed(true);
        otpRepository.save(otp);
    }
}