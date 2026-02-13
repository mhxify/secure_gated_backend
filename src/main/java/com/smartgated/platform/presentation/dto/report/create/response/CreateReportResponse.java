package com.smartgated.platform.presentation.dto.report.create.response;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateReportResponse {

    private UUID reportId;

    private String content;

    private LocalDateTime sentAt;

    private String imageUrl;

    public void setReportId(UUID reportId) {
        this.reportId = reportId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UUID getReportId() {
        return reportId;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
