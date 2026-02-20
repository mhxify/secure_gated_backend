package com.smartgated.platform.presentation.dto.guestRequest.update.status;

import com.smartgated.platform.domain.enums.guest.GuestRequestStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateGuestRequestStatusRequest {
    private GuestRequestStatus requestStatus;
}
