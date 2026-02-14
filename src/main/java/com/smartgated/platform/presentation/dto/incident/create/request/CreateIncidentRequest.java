package com.smartgated.platform.presentation.dto.incident.create.request;

import java.util.UUID;

public class CreateIncidentRequest {

    private String incidentDescription;

    private String categoryName;

    private UUID userId;

    public String getIncidentDescription() {
        return incidentDescription;
    }

    public void setIncidentDescription(String incidentDescription) {
        this.incidentDescription = incidentDescription;
    }


    public void setCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategory() {
        return categoryName;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

}
