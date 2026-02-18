package com.smartgated.platform.presentation.dto.assignedShift.create.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AssignShiftRequest {

    private UUID userId;
    private UUID shiftId;

}
