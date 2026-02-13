package com.smartgated.platform.presentation.dto.report.adminReply;

import java.time.LocalDateTime;

public class AdminReplyReportRequest {
    private String adminResponse;

    private LocalDateTime repliedAt;


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
}
