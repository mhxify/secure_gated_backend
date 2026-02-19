package com.smartgated.platform.infrastructure.repository.reservation;

import com.smartgated.platform.domain.model.reservation.Reservation;
import com.smartgated.platform.domain.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    List<Reservation> findByUser(User user);

    List<Reservation> findByUser_UserId(UUID userId);
}