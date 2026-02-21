package com.smartgated.platform.presentation.dto.auth.response;

import com.smartgated.platform.domain.enums.user.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MeResponse {
    private UUID userId;
    private String fullname;
    private String email;
    private UserRole role;
    private String imageUrl;
}