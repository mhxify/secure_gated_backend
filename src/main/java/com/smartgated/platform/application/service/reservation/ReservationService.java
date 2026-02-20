package com.smartgated.platform.application.service.reservation;


import com.smartgated.platform.application.usecase.reservation.ReservationUseCase;
import com.smartgated.platform.domain.enums.reservation.ReservationStatus;
import com.smartgated.platform.domain.enums.user.UserRole;
import com.smartgated.platform.domain.model.notification.Notification;
import com.smartgated.platform.domain.model.reservation.Reservation;
import com.smartgated.platform.domain.model.users.User;
import com.smartgated.platform.infrastructure.repository.notification.NotificationRepository;
import com.smartgated.platform.infrastructure.repository.reservation.ReservationRepository;
import com.smartgated.platform.infrastructure.repository.user.UserRepository;
import com.smartgated.platform.presentation.dto.reservation.create.request.CreateReservationRequest;
import com.smartgated.platform.presentation.dto.reservation.create.response.CreateReservationResponse;
import com.smartgated.platform.presentation.dto.reservation.get.GetReservation;
import com.smartgated.platform.presentation.dto.reservation.update.request.UpdateReservationRequest;
import com.smartgated.platform.presentation.dto.reservation.update.status.UpdateReservationStatusRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ReservationService implements ReservationUseCase {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository ;

    public ReservationService(
            ReservationRepository reservationRepository,
            UserRepository userRepository ,
            NotificationRepository notificationRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository ;
    }

    @Override
    public CreateReservationResponse createReservation(CreateReservationRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Reservation r = new Reservation();
        r.setUser(user);
        r.setReservationDate(request.getReservationDate());
        r.setStartTime(request.getStartTime());
        r.setEndTime(request.getEndTime());
        r.setNumberOfGuests(request.getNumberOfGuests());

        r.setCreatedAt(LocalDateTime.now());
        r.setStatus(ReservationStatus.PENDING);

        Reservation saved = reservationRepository.save(r);

        List<User> admins = userRepository.findByRole(UserRole.ADMIN);

        String content = "New reservation request from " + user.getFullname()
                + " on " + saved.getReservationDate()
                + " (" + saved.getStartTime() + " - " + saved.getEndTime() + ").";

        List<Notification> notifications = admins.stream().map(admin -> {
            Notification n = new Notification();
            n.setUser(admin);
            n.setCreatedAt(LocalDateTime.now());
            n.setRead(false);
            n.setContent(content);
            return n;
        }).toList();

        if (!notifications.isEmpty()) {
            notificationRepository.saveAll(notifications);
        }


        CreateReservationResponse response = new CreateReservationResponse();
        response.setReservationId(saved.getReservationId());
        return response;
    }

    @Override
    public void updateReservation(UUID reservationId, UUID userId, UpdateReservationRequest request) {

        Reservation r = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (r.getUser() == null || !r.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("Not allowed: reservation is not yours");
        }

        r.setReservationDate(request.getReservationDate());
        r.setStartTime(request.getStartTime());
        r.setEndTime(request.getEndTime());
        r.setNumberOfGuests(request.getNumberOfGuests());

        reservationRepository.save(r);

        List<User> admins = userRepository.findByRole(UserRole.ADMIN);

        String content = "Reservation updated by " + r.getUser().getFullname()
                + " (ReservationId: " + r.getReservationId() + ").";

        notificationRepository.saveAll(
                admins.stream().map(admin -> {
                    Notification n = new Notification();
                    n.setUser(admin);
                    n.setCreatedAt(LocalDateTime.now());
                    n.setRead(false);
                    n.setContent(content);
                    return n;
                }).toList()
        );

    }

    @Override
    public void updateReservationStatus(UUID reservationId, UpdateReservationStatusRequest request) {
        Reservation r = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        r.setStatus(request.getStatus());
        reservationRepository.save(r);
        Reservation saved = reservationRepository.save(r);

        User owner = saved.getUser();
        if (owner != null) {
            Notification n = new Notification();
            n.setUser(owner);
            n.setCreatedAt(LocalDateTime.now());
            n.setRead(false);
            n.setContent("Your reservation (" + saved.getReservationId() + ") status is now: "
                    + saved.getStatus().name());
            notificationRepository.save(n);
        }

    }

    @Override
    public void deleteReservation(UUID reservationId, UUID userId) {
        Reservation r = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (r.getUser() == null || !r.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("Not allowed: reservation is not yours");
        }

        reservationRepository.delete(r);
    }

    @Override
    @Transactional(readOnly = true)
    public GetReservation getReservationById(UUID reservationId) {
        Reservation r = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        return toDto(r);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetReservation> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetReservation> getMyReservations(UUID userId) {
        return reservationRepository.findByUser_UserId(userId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private GetReservation toDto(Reservation r) {
        GetReservation dto = new GetReservation();
        dto.setReservationId(r.getReservationId());
        dto.setReservationDate(r.getReservationDate());
        dto.setStartTime(r.getStartTime());
        dto.setEndTime(r.getEndTime());
        dto.setCreatedAt(r.getCreatedAt());
        dto.setStatus(r.getStatus());
        dto.setNumberOfGuests(r.getNumberOfGuests());
        dto.setUserId(r.getUser() != null ? r.getUser().getUserId() : null);
        return dto;
    }
}

