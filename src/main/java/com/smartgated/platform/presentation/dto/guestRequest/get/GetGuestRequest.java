package com.smartgated.platform.presentation.dto.guestRequest.get;

import com.smartgated.platform.domain.enums.guest.GuestRequestStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class GetGuestRequest {

    private UUID requestId;
    private UUID userId;

    private String guestEmail;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private GuestRequestStatus requestStatus;
    private LocalDateTime createdAt;

}