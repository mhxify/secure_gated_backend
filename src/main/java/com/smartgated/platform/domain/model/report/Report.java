package com.smartgated.platform.domain.model.report;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.grammars.hql.HqlParser.DateTimeContext;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartgated.platform.domain.model.users.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reports")
public class Report {
    
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID reportId;

    private String content;

    private LocalDateTime sentAt;

    private String adminResponse;

    private LocalDateTime repliedAt;

    private String imageUrl;

    @ManyToOne
    @JsonIgnore
    private User user;

    @JsonProperty("userId")
    public UUID getUserId() {
        return user != null ? user.getUserId() : null;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    
    
    public UUID getReportId() {
        return reportId;
    }

    public void setReportId(UUID reportId) {
        this.reportId = reportId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public String getAdminResponse() {
        return adminResponse;
    }

    public void setAdminResponse(String adminResponse) {
        this.adminResponse = adminResponse;
    }

    public LocalDateTime getRepliedAt() {
        return repliedAt;
    }

    public void setRepliedAt(LocalDateTime repliedAt) {
        this.repliedAt = repliedAt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public Report() {
    }

    public Report(String content, LocalDateTime sentAt, String imageUrl) {
        this.content = content;
        this.sentAt = sentAt;
        this.imageUrl = imageUrl;
    }



}
