package com.smartgated.platform.application.usecase.incident;

import java.util.List;
import java.util.UUID;

import com.smartgated.platform.presentation.dto.incident.create.request.CreateIncidentRequest;
import com.smartgated.platform.presentation.dto.incident.create.response.CreateIncidentResponse;
import com.smartgated.platform.presentation.dto.incident.get.GetIncident;

public interface IncidentUseCase {
    
    CreateIncidentResponse createIncident(CreateIncidentRequest request);

    GetIncident getIncidentById(UUID incidentId);

    List<GetIncident> getIncidentsByUserId(UUID userId);

    List<GetIncident> getIncidentsByStatus(String status);

    Void updateIncidentStatus(UUID incidentId, String status);

    void deleteIncident(UUID incidentId);

    List<GetIncident> getAllIncidents();
}
