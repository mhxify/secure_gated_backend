package com.smartgated.platform.presentation.dto.assignedShift.get;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class GetAssignedShift {
    private UUID assignId;
    private UUID shiftId;
    private Time startTime;
    private Time endTime;
    private Date onDate ;
}
