package com.smartgated.platform.presentation.dto.reservation.update.status;

import com.smartgated.platform.domain.enums.reservation.ReservationStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateReservationStatusRequest {
    private ReservationStatus status;
}