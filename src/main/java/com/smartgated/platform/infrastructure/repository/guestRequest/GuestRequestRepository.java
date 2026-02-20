package com.smartgated.platform.infrastructure.repository.guestRequest;

import com.smartgated.platform.domain.enums.guest.GuestRequestStatus;
import com.smartgated.platform.domain.model.guestRequest.GuestRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GuestRequestRepository extends JpaRepository<GuestRequest, UUID> {

    List<GuestRequest> findByUser_UserId(UUID userId);

    List<GuestRequest> findByRequestStatus(GuestRequestStatus status);

    List<GuestRequest> findByUser_UserIdAndRequestStatus(UUID userId, GuestRequestStatus status);
}
