package com.smartgated.platform.application.service.report;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.smartgated.platform.application.usecase.report.ReportUseCase;
import com.smartgated.platform.domain.model.report.Report;
import com.smartgated.platform.domain.model.users.User;
import com.smartgated.platform.infrastructure.repository.report.ReportRepository;
import com.smartgated.platform.infrastructure.repository.user.UserRepository;
import com.smartgated.platform.presentation.dto.report.adminReply.AdminReplyReportRequest;
import com.smartgated.platform.presentation.dto.report.create.request.CreateReportRequest;
import com.smartgated.platform.presentation.dto.report.create.response.CreateReportResponse;

@Service
public class ReportService implements ReportUseCase {

    private final ReportRepository reportRepository;

    private final UserRepository userRepository;

    public ReportService(ReportRepository reportRepository,
                        UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    
    @Override
    public List<Report> getAllReports() {
        
        return reportRepository.findAll();
    }

    @Override
    public Report getReportById(UUID reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found: " + reportId));
    }

    @Override
    public List<Report> getReportsByUserId(UUID userId) {
        return reportRepository.findByUser_UserId(userId);
    }

    @Override
    public Report updateReport(UUID reportId, AdminReplyReportRequest report) {
        Report existingReport = getReportById(reportId);
        existingReport.setAdminResponse(report.getAdminResponse());
        existingReport.setRepliedAt(report.getRepliedAt());

        return reportRepository.save(existingReport);
    }

    @Override
    public void deleteReport(UUID reportId) {
        reportRepository.deleteById(reportId);
    }

    @Override
    public CreateReportResponse createReport(CreateReportRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Report report = new Report();

        report.setContent(request.getContent());
        report.setImageUrl(request.getImageUrl());
        report.setSentAt(LocalDateTime.now());
        report.setAdminResponse(null);
        report.setRepliedAt(null);
        report.setUser(user);

        Report saved = reportRepository.save(report);

        CreateReportResponse response = new CreateReportResponse();
        response.setReportId(saved.getReportId());
        response.setContent(saved.getContent());
        response.setSentAt(saved.getSentAt());
        response.setImageUrl(saved.getImageUrl());

        return response;
    }



}
