package com.smartgated.platform.application.usecase.report;

import java.util.List;
import java.util.UUID;

import com.smartgated.platform.domain.model.report.Report;
import com.smartgated.platform.presentation.dto.report.adminReply.AdminReplyReportRequest;
import com.smartgated.platform.presentation.dto.report.create.request.CreateReportRequest;
import com.smartgated.platform.presentation.dto.report.create.response.CreateReportResponse;
import com.smartgated.platform.presentation.dto.report.get.GetReport;

public interface ReportUseCase {

    List<GetReport> getAllReports();

    GetReport getReportById(UUID reportId);

    List<GetReport> getReportsByUserId(UUID userId);

    CreateReportResponse createReport(CreateReportRequest report);

    GetReport updateReport(UUID reportId, AdminReplyReportRequest report);

    void deleteReport(UUID reportId);

}
