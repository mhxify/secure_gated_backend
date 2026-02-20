package com.smartgated.platform.presentation.controller.guestRequest;


import com.smartgated.platform.application.usecase.guestRequest.GuestRequestUseCase;
import com.smartgated.platform.domain.enums.guest.GuestRequestStatus;
import com.smartgated.platform.presentation.dto.guestRequest.create.request.CreateGuestRequest;
import com.smartgated.platform.presentation.dto.guestRequest.create.response.CreateGuestRequestResponse;
import com.smartgated.platform.presentation.dto.guestRequest.get.GetGuestRequest;
import com.smartgated.platform.presentation.dto.guestRequest.update.status.UpdateGuestRequestStatusRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/guest-requests")
public class GuestRequestController {

    private final GuestRequestUseCase guestRequestUseCase;

    public GuestRequestController(GuestRequestUseCase guestRequestUseCase) {
        this.guestRequestUseCase = guestRequestUseCase;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateGuestRequestResponse> create(@RequestBody CreateGuestRequest request) {
        return ResponseEntity.ok(guestRequestUseCase.createGuestRequest(request));
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID requestId,
            @RequestParam UUID userId
    ) {
        guestRequestUseCase.deleteGuestRequest(requestId, userId);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{requestId}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable UUID requestId,
            @RequestBody UpdateGuestRequestStatusRequest request
    ) {
        GuestRequestStatus status = request.getRequestStatus();
        guestRequestUseCase.updateRequestStatus(requestId, status);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<GetGuestRequest> getById(@PathVariable UUID requestId) {
        return ResponseEntity.ok(guestRequestUseCase.getById(requestId));
    }

    @GetMapping("/me")
    public ResponseEntity<List<GetGuestRequest>> myRequests(@RequestParam UUID userId) {
        return ResponseEntity.ok(guestRequestUseCase.getMyRequests(userId));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<GetGuestRequest>> pending() {
        return ResponseEntity.ok(guestRequestUseCase.getPendingRequests());
    }
}

