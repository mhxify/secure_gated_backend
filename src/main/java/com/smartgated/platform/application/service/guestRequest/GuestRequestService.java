package com.smartgated.platform.application.service.guestRequest;

import com.smartgated.platform.application.usecase.guestRequest.GuestRequestUseCase;
import com.smartgated.platform.domain.enums.guest.GuestRequestStatus;
import com.smartgated.platform.domain.enums.user.UserRole;
import com.smartgated.platform.domain.model.guestRequest.GuestRequest;
import com.smartgated.platform.domain.model.notification.Notification;
import com.smartgated.platform.domain.model.users.User;
import com.smartgated.platform.infrastructure.repository.guestRequest.GuestRequestRepository;
import com.smartgated.platform.infrastructure.repository.notification.NotificationRepository;
import com.smartgated.platform.infrastructure.repository.user.UserRepository;
import com.smartgated.platform.presentation.dto.guestRequest.create.request.CreateGuestRequest;
import com.smartgated.platform.presentation.dto.guestRequest.create.response.CreateGuestRequestResponse;
import com.smartgated.platform.presentation.dto.guestRequest.get.GetGuestRequest;
import com.smartgated.platform.presentation.dto.guestRequest.update.status.UpdateGuestRequestStatusRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class GuestRequestService implements GuestRequestUseCase {

    private final GuestRequestRepository guestRequestRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository ;

    public GuestRequestService(
            GuestRequestRepository guestRequestRepository,
            UserRepository userRepository ,
            NotificationRepository notificationRepository
    ) {
        this.guestRequestRepository = guestRequestRepository;
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository ;
    }

    @Override
    public CreateGuestRequestResponse createGuestRequest(CreateGuestRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        GuestRequest gr = new GuestRequest();
        gr.setUser(user);
        gr.setGuestEmail(request.getGuestEmail());
        gr.setStartTime(request.getStartTime());
        gr.setEndTime(request.getEndTime());
        gr.setCreatedAt(LocalDateTime.now());
        gr.setRequestStatus(GuestRequestStatus.PENDING);

        GuestRequest saved = guestRequestRepository.save(gr);

        List<User> admins = userRepository.findByRole(UserRole.ADMIN);

        String content = "New guest request from " + user.getFullname()
                + " for " + saved.getGuestEmail()
                + " (" + saved.getStartTime() + " → " + saved.getEndTime() + ").";

        List<Notification> notifications = admins.stream().map(admin -> {
            Notification n = new Notification();
            n.setUser(admin);
            n.setContent(content);
            n.setCreatedAt(LocalDateTime.now());
            n.setRead(false);
            return n;
        }).toList();

        notificationRepository.saveAll(notifications);

        CreateGuestRequestResponse response = new CreateGuestRequestResponse();
        response.setRequestId(saved.getRequestId());
        return response;
    }

    @Override
    public void deleteGuestRequest(UUID requestId, UUID userId) {
        GuestRequest gr = guestRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Guest request not found"));

        if (gr.getUser() == null || !gr.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("Not allowed: request is not yours");
        }

        guestRequestRepository.delete(gr);
    }

    @Override
    public void updateRequestStatus(UUID requestId, GuestRequestStatus status) {

        GuestRequest gr = guestRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Guest request not found"));

        gr.setRequestStatus(status);
        GuestRequest saved = guestRequestRepository.save(gr);

        User owner = saved.getUser();
        if (owner != null) {
            Notification n = new Notification();
            n.setUser(owner);
            n.setCreatedAt(LocalDateTime.now());
            n.setRead(false);

            String content = "Your guest request for " + saved.getGuestEmail()
                    + " has been " + status.name() + ".";
            n.setContent(content);

            notificationRepository.save(n);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public GetGuestRequest getById(UUID requestId) {
        GuestRequest gr = guestRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Guest request not found"));

        return toDto(gr);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetGuestRequest> getMyRequests(UUID userId) {
        return guestRequestRepository.findByUser_UserId(userId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetGuestRequest> getPendingRequests() {
        return guestRequestRepository.findByRequestStatus(GuestRequestStatus.PENDING)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private GetGuestRequest toDto(GuestRequest gr) {
        GetGuestRequest dto = new GetGuestRequest();
        dto.setRequestId(gr.getRequestId());
        dto.setGuestEmail(gr.getGuestEmail());
        dto.setStartTime(gr.getStartTime());
        dto.setEndTime(gr.getEndTime());
        dto.setCreatedAt(gr.getCreatedAt());
        dto.setRequestStatus(gr.getRequestStatus());
        dto.setUserId(gr.getUser() != null ? gr.getUser().getUserId() : null);
        return dto;
    }
}
