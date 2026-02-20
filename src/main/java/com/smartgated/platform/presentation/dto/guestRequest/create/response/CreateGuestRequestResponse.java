package com.smartgated.platform.presentation.dto.guestRequest.create.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateGuestRequestResponse {
    private UUID requestId;
}
