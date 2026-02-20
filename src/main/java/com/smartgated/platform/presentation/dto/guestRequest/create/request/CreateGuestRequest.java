package com.smartgated.platform.presentation.dto.guestRequest.create.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CreateGuestRequest {
    private UUID userId;
    private String guestEmail;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
