package com.smartgated.platform.application.usecase.reservation;

import com.smartgated.platform.presentation.dto.reservation.create.request.CreateReservationRequest;
import com.smartgated.platform.presentation.dto.reservation.create.response.CreateReservationResponse;
import com.smartgated.platform.presentation.dto.reservation.get.GetReservation;
import com.smartgated.platform.presentation.dto.reservation.update.request.UpdateReservationRequest;
import com.smartgated.platform.presentation.dto.reservation.update.status.UpdateReservationStatusRequest;

import java.util.List;
import java.util.UUID;

public interface ReservationUseCase {

    CreateReservationResponse createReservation(CreateReservationRequest request);

    void updateReservation(UUID reservationId, UUID userId, UpdateReservationRequest request);

    void updateReservationStatus(UUID reservationId, UpdateReservationStatusRequest request);

    void deleteReservation(UUID reservationId, UUID userId);

    GetReservation getReservationById(UUID reservationId);

    List<GetReservation> getAllReservations();

    List<GetReservation> getMyReservations(UUID userId);
}
