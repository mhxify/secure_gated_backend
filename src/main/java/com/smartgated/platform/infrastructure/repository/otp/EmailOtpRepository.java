package com.smartgated.platform.infrastructure.repository.otp;

import com.smartgated.platform.domain.model.otp.EmailOtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmailOtpRepository extends JpaRepository<EmailOtp, UUID> {
    Optional<EmailOtp> findTopByEmailOrderByExpiresAtDesc(String email);
}