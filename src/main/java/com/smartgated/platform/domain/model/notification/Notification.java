package com.smartgated.platform.domain.model.notification;

import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifications")
public class Notification {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID notificationId;
    private String content;
    private boolean isRead;
    private DateTimeFormat createdAt;

    public Notification() {
    }

    public Notification(String content, boolean isRead, DateTimeFormat createdAt) {
        this.content = content;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    public UUID getNotificationId() {
        return notificationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    public DateTimeFormat getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTimeFormat createdAt) {
        this.createdAt = createdAt;
    }
    

}
