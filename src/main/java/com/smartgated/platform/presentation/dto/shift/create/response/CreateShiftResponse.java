package com.smartgated.platform.presentation.dto.shift.create.response;

import java.util.UUID;

public class CreateShiftResponse {

    private UUID shiftId ;

    public void setShiftId(UUID shiftId) {
        this.shiftId = shiftId;
    }

    public UUID getShiftId() {
        return shiftId;
    }


}
