package com.smartgated.platform.presentation.controller.reservation;

import com.smartgated.platform.application.usecase.reservation.ReservationUseCase;
import com.smartgated.platform.presentation.dto.reservation.create.request.CreateReservationRequest;
import com.smartgated.platform.presentation.dto.reservation.create.response.CreateReservationResponse;
import com.smartgated.platform.presentation.dto.reservation.get.GetReservation;
import com.smartgated.platform.presentation.dto.reservation.update.request.UpdateReservationRequest;
import com.smartgated.platform.presentation.dto.reservation.update.status.UpdateReservationStatusRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationUseCase reservationUseCase;

    public ReservationController(ReservationUseCase reservationUseCase) {
        this.reservationUseCase = reservationUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateReservationResponse> create(@RequestBody CreateReservationRequest request) {
        return ResponseEntity.ok(reservationUseCase.createReservation(request));
    }

    @PatchMapping("/{reservationId}")
    public ResponseEntity<Void> update(
            @PathVariable UUID reservationId,
            @RequestParam UUID userId,
            @RequestBody UpdateReservationRequest request
    ) {
        reservationUseCase.updateReservation(reservationId, userId, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{reservationId}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable UUID reservationId,
            @RequestBody UpdateReservationStatusRequest request
    ) {
        reservationUseCase.updateReservationStatus(reservationId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID reservationId,
            @RequestParam UUID userId
    ) {
        reservationUseCase.deleteReservation(reservationId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<GetReservation> getById(@PathVariable UUID reservationId) {
        return ResponseEntity.ok(reservationUseCase.getReservationById(reservationId));
    }

    @GetMapping
    public ResponseEntity<List<GetReservation>> getAll() {
        return ResponseEntity.ok(reservationUseCase.getAllReservations());
    }

    @GetMapping("/me")
    public ResponseEntity<List<GetReservation>> getMine(@RequestParam UUID userId) {
        return ResponseEntity.ok(reservationUseCase.getMyReservations(userId));
    }
}

