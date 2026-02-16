package com.smartgated.platform.presentation.dto.facilities.create.response;

import java.util.UUID;

public class CreateFacilityResponse {
    private UUID facilityId;

    public void setFacilityId(UUID facilityId) {
        this.facilityId = facilityId;
    }

    public UUID getFacilityId() {
        return facilityId;
    }
}
