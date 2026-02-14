package com.smartgated.platform.domain.model.category;

import java.time.LocalDateTime;
import java.util.UUID;

import com.smartgated.platform.domain.model.incident.Incident;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {


    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID categoryId;

    private String categoryName;

    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "incident_id", unique = true)
    private Incident incident;


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public Incident getIncident() {
        return incident;
    }

    public void setIncident(Incident incident) {
        this.incident = incident;
    }

    
    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    public Category() {
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category(UUID categoryId, String categoryName , LocalDateTime createdAt) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.createdAt = createdAt;

    }

    public Category orElseThrow(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'orElseThrow'");
    }


}
