package com.smartgated.platform.presentation.dto.reservation.create.request;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;

@Getter
@Setter
public class CreateReservationRequest {
    private UUID userId;
    private Date reservationDate;
    private Time startTime;
    private Time endTime;
    private Long numberOfGuests;
}