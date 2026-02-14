package com.smartgated.platform.infrastructure.repository.incident;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartgated.platform.domain.enums.incident.IncidentStatus;
import com.smartgated.platform.domain.model.incident.Incident;

public interface IncidentRepository extends JpaRepository<Incident, UUID> {

    List<Incident> findByUserUserId(UUID userId);

    List<Incident> findByCategoryCategoryId(UUID categoryId);

    List<Incident> findByStatus(IncidentStatus status);    
}
