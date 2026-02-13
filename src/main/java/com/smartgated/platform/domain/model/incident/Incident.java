package com.smartgated.platform.domain.model.incident;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.grammars.hql.HqlParser.DateTimeContext;

import com.smartgated.platform.domain.enums.incident.IncidentStatus;
import com.smartgated.platform.domain.model.category.Category;
import com.smartgated.platform.domain.model.users.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "incidents")
public class Incident {
    
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID incidentId;

    private String incidentDescription;

    private LocalDateTime reportedAt;

    private IncidentStatus status;

    @OneToOne(mappedBy = "incident", fetch = FetchType.LAZY)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


     public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public UUID getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(UUID incidentId) {
        this.incidentId = incidentId;
    }

    public String getIncidentDescription() {
        return incidentDescription;
    }

    public void setIncidentDescription(String incidentDescription) {
        this.incidentDescription = incidentDescription;
    }

    public LocalDateTime getReportedAt() {
        return reportedAt;
    }

    public void setReportedAt(LocalDateTime reportedAt) {
        this.reportedAt = reportedAt;
    }


    public IncidentStatus getStatus() {
        return status;
    }

    public void setStatus(IncidentStatus status) {
        this.status = status;
    }

    public Incident() {
    }

    public Incident(
        String incidentDescription,
        LocalDateTime reportedAt, 
        IncidentStatus status,
        Category category
    ) {
        this.incidentDescription = incidentDescription;
        this.reportedAt = reportedAt;
        this.status = status;
        this.category = category;
    }
}
