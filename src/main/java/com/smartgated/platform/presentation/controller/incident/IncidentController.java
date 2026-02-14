package com.smartgated.platform.presentation.controller.incident;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartgated.platform.application.usecase.incident.IncidentUseCase;
import com.smartgated.platform.presentation.dto.category.create.request.CreateCategoryRequest;
import com.smartgated.platform.presentation.dto.category.create.response.CreateCategoryResponse;
import com.smartgated.platform.presentation.dto.incident.create.request.CreateIncidentRequest;
import com.smartgated.platform.presentation.dto.incident.create.response.CreateIncidentResponse;
import com.smartgated.platform.presentation.dto.incident.get.GetIncident;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/incidents")
public class IncidentController {

    private final IncidentUseCase incidentUseCase;

    public IncidentController(IncidentUseCase incidentUseCase) {
        this.incidentUseCase = incidentUseCase;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateIncidentResponse> postMethodName(
        @RequestBody CreateIncidentRequest request
    ) {

        CreateIncidentResponse response = incidentUseCase.createIncident(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{incidentId}")
    public ResponseEntity<GetIncident> getIncidentById(
            @PathVariable UUID incidentId) {

        GetIncident response = incidentUseCase.getIncidentById(incidentId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{incidentId}/status")
    public ResponseEntity<Void> updateIncidentStatus(
            @PathVariable UUID incidentId,
            @RequestParam String status) {

        incidentUseCase.updateIncidentStatus(incidentId, status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{incidentId}")
    public ResponseEntity<Void> deleteIncident(
            @PathVariable UUID incidentId) {

        incidentUseCase.deleteIncident(incidentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GetIncident>> getIncidentsByUserId(
            @PathVariable UUID userId) {

        return ResponseEntity.ok(
                incidentUseCase.getIncidentsByUserId(userId)
        );
    }


    @GetMapping("/status")
    public ResponseEntity<List<GetIncident>> getIncidentsByStatus(
            @RequestParam String status
    ) {

        return ResponseEntity.ok(
                incidentUseCase.getIncidentsByStatus(status)
        );
    }

    @GetMapping("/all")
    public ResponseEntity<List<GetIncident>> getAllIncidents() {
        return ResponseEntity.ok(incidentUseCase.getAllIncidents());
    }
    
    
}
