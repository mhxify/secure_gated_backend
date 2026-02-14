package com.smartgated.platform.application.service.incident;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.smartgated.platform.application.usecase.incident.IncidentUseCase;
import com.smartgated.platform.domain.enums.incident.IncidentStatus;
import com.smartgated.platform.domain.model.category.Category;
import com.smartgated.platform.domain.model.incident.Incident;
import com.smartgated.platform.domain.model.users.User;
import com.smartgated.platform.infrastructure.repository.category.CategoryRepository;
import com.smartgated.platform.infrastructure.repository.incident.IncidentRepository;
import com.smartgated.platform.infrastructure.repository.user.UserRepository;
import com.smartgated.platform.presentation.dto.incident.create.request.CreateIncidentRequest;
import com.smartgated.platform.presentation.dto.incident.create.response.CreateIncidentResponse;
import com.smartgated.platform.presentation.dto.incident.get.GetIncident;

@Service
public class IncidentSevice implements IncidentUseCase { 

    private final IncidentRepository incidentRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;


    public IncidentSevice(
        IncidentRepository incidentRepository,
        UserRepository userRepository,
        CategoryRepository categoryRepository
    ) {
        this.incidentRepository = incidentRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public CreateIncidentResponse createIncident(CreateIncidentRequest request) {
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = categoryRepository.findByCategoryName(request.getCategory());

        if (category == null) {
            throw new RuntimeException("Category not found");
        }



        Incident incident = new Incident();
        incident.setIncidentDescription(request.getIncidentDescription());
        incident.setCategory(category);
        incident.setUser(user);
        incident.setStatus(IncidentStatus.PENDING);
        incident.setReportedAt(LocalDateTime.now());

        Incident savedIncident = incidentRepository.save(incident);

        CreateIncidentResponse response = new CreateIncidentResponse();

        response.setIncidentId(savedIncident.getIncidentId());
        return response;
    }


    @Override
    public GetIncident getIncidentById(UUID incidentId) {
        Incident incident = incidentRepository.findById(incidentId)
            .orElseThrow(() -> new RuntimeException("Incident not found"));

        GetIncident response = new GetIncident();
        response.setIncidentId(incident.getIncidentId());
        response.setIncidentDescription(incident.getIncidentDescription());
        response.setCategoryName(incident.getCategory().getCategoryName());
        response.setStatus(incident.getStatus().name());
        response.setReportedAt(incident.getReportedAt());
        return response;
    }


    @Override
    public Void updateIncidentStatus(UUID incidentId, String status) {
        Incident incident = incidentRepository.findById(incidentId)
            .orElseThrow(() -> new RuntimeException("Incident not found"));

        try {
            IncidentStatus newStatus = IncidentStatus.valueOf(status.toUpperCase());
            incident.setStatus(newStatus);
            incidentRepository.save(incident);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status value");
        }

        return null;
    }

    @Override
    public void deleteIncident(UUID incidentId) {
        Incident incident = incidentRepository.findById(incidentId)
            .orElseThrow(() -> new RuntimeException("Incident not found"));

        incidentRepository.delete(incident);
    }

    @Override
    public List<GetIncident> getIncidentsByUserId(UUID userId) {
        List<Incident> incidents = incidentRepository.findByUserUserId(userId);

        return incidents.stream().map(incident -> {
            GetIncident response = new GetIncident();
            response.setIncidentId(incident.getIncidentId());
            response.setIncidentDescription(incident.getIncidentDescription());
            response.setCategoryName(incident.getCategory().getCategoryName());
            response.setStatus(incident.getStatus().name());
            response.setReportedAt(incident.getReportedAt());
            return response;
        }).collect(Collectors.toList());
    }


    @Override
    public List<GetIncident> getIncidentsByStatus(String status) {
        try {
            IncidentStatus incidentStatus = IncidentStatus.valueOf(status.toUpperCase());
            List<Incident> incidents = incidentRepository.findByStatus(incidentStatus);

            return incidents.stream().map(incident -> {
                GetIncident response = new GetIncident();
                response.setIncidentId(incident.getIncidentId());
                response.setIncidentDescription(incident.getIncidentDescription());
                response.setCategoryName(incident.getCategory().getCategoryName());
                response.setStatus(incident.getStatus().name());
                response.setReportedAt(incident.getReportedAt());
                return response;
            }).collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status value");
        }
    }

    @Override
    public List<GetIncident> getAllIncidents() {
        List<Incident> incidents = incidentRepository.findAll();

        return incidents.stream().map(incident -> {
            GetIncident response = new GetIncident();
            response.setIncidentId(incident.getIncidentId());
            response.setIncidentDescription(incident.getIncidentDescription());
            response.setCategoryName(incident.getCategory().getCategoryName());
            response.setStatus(incident.getStatus().name());
            response.setReportedAt(incident.getReportedAt());
            return response;
        }).collect(Collectors.toList());
    }

    
}
