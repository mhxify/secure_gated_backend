package com.smartgated.platform.presentation.dto.reservation.create.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateReservationResponse {
    private UUID reservationId;
}
