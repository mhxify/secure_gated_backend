package com.smartgated.platform.presentation.dto.incident.create.response;

import java.util.UUID;

public class CreateIncidentResponse {

    private UUID incidentId;

    public UUID getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(UUID incidentId) {
        this.incidentId = incidentId;
    }
    
}
