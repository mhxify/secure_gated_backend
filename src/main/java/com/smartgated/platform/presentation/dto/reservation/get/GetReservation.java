package com.smartgated.platform.presentation.dto.reservation.get;

import com.smartgated.platform.domain.enums.reservation.ReservationStatus;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class GetReservation {
    private UUID reservationId;
    private UUID userId;

    private Date reservationDate;
    private Time startTime;
    private Time endTime;

    private LocalDateTime createdAt;
    private ReservationStatus status;

    private Long numberOfGuests;

}