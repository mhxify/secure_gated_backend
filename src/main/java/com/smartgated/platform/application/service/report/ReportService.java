package com.smartgated.platform.application.service.report;

import com.smartgated.platform.application.usecase.report.ReportUseCase;
import com.smartgated.platform.domain.enums.user.UserRole;
import com.smartgated.platform.domain.model.notification.Notification;
import com.smartgated.platform.domain.model.report.Report;
import com.smartgated.platform.domain.model.users.User;
import com.smartgated.platform.infrastructure.repository.notification.NotificationRepository;
import com.smartgated.platform.infrastructure.repository.report.ReportRepository;
import com.smartgated.platform.infrastructure.repository.user.UserRepository;
import com.smartgated.platform.presentation.dto.report.adminReply.AdminReplyReportRequest;
import com.smartgated.platform.presentation.dto.report.create.request.CreateReportRequest;
import com.smartgated.platform.presentation.dto.report.create.response.CreateReportResponse;
import com.smartgated.platform.presentation.dto.report.get.GetReport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ReportService implements ReportUseCase {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    public ReportService(
            ReportRepository reportRepository,
            UserRepository userRepository,
            NotificationRepository notificationRepository
    ) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    private Report getReportEntity(UUID reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found: " + reportId));
    }

    private GetReport toDto(Report r) {
        GetReport dto = new GetReport();
        dto.setContent(r.getContent());
        dto.setSentAt(r.getSentAt());
        dto.setImageUrl(r.getImageUrl());
        dto.setUserId(r.getUserId() != null ? r.getUserId() : null);
        dto.setRepliedAt(r.getRepliedAt());
        dto.setAdminReply(r.getAdminResponse());
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetReport> getAllReports() {
        return reportRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public GetReport getReportById(UUID reportId) {
        return toDto(getReportEntity(reportId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetReport> getReportsByUserId(UUID userId) {
        return reportRepository.findByUser_UserId(userId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public GetReport updateReport(UUID reportId, AdminReplyReportRequest request) {


        Report existing = getReportEntity(reportId);
        User owner = userRepository.findById(existing.getUserId())
                        .orElseThrow(() -> new RuntimeException("The user not found")) ;

        existing.setAdminResponse(request.getAdminResponse());

        existing.setRepliedAt(LocalDateTime.now());

        Report saved = reportRepository.save(existing);

        Notification n = new Notification();
        n.setUser(owner);
        n.setCreatedAt(LocalDateTime.now());
        n.setRead(false);
        n.setContent("Admin replied to your report (ReportId: " + saved.getReportId() + ").");
        notificationRepository.save(n);

        return toDto(saved);
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

        List<User> admins = userRepository.findByRole(UserRole.ADMIN);

        String notifContent = "New report from " + user.getFullname()
                + " (ReportId: " + saved.getReportId() + ").";

        List<Notification> notifications = admins.stream().map(admin -> {
            Notification n = new Notification();
            n.setUser(admin);
            n.setCreatedAt(LocalDateTime.now());
            n.setRead(false);
            n.setContent(notifContent);
            return n;
        }).toList();

        if (!notifications.isEmpty()) {
            notificationRepository.saveAll(notifications);
        }

        CreateReportResponse response = new CreateReportResponse();
        response.setReportId(saved.getReportId());
        response.setContent(saved.getContent());
        response.setSentAt(saved.getSentAt());
        response.setImageUrl(saved.getImageUrl());
        return response;
    }
}