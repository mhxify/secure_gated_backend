package com.smartgated.platform.presentation.dto.assignedShift.create.response;

import java.util.UUID;

public class AssignShiftResponse {

    private UUID assignId ;

    public void setAssignId(UUID assignId) {
        this.assignId = assignId;
    }

    public UUID getAssignId() {
        return assignId;
    }


}
