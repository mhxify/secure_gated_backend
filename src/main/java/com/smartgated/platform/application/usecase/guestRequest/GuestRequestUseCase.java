package com.smartgated.platform.application.usecase.guestRequest;

import com.smartgated.platform.domain.enums.guest.GuestRequestStatus;
import com.smartgated.platform.presentation.dto.guestRequest.create.request.CreateGuestRequest;
import com.smartgated.platform.presentation.dto.guestRequest.create.response.CreateGuestRequestResponse;
import com.smartgated.platform.presentation.dto.guestRequest.get.GetGuestRequest;
import com.smartgated.platform.presentation.dto.guestRequest.update.status.UpdateGuestRequestStatusRequest;

import java.util.List;
import java.util.UUID;

public interface GuestRequestUseCase {

    CreateGuestRequestResponse createGuestRequest(CreateGuestRequest request);

    void deleteGuestRequest(UUID requestId, UUID userId);

    void updateRequestStatus(UUID requestId, GuestRequestStatus status);

    GetGuestRequest getById(UUID requestId);

    List<GetGuestRequest> getMyRequests(UUID userId);

    List<GetGuestRequest> getPendingRequests(); // for guard/admin
}

