package com.smartgated.platform.presentation.controller.report;

import java.util.List;
import java.util.UUID;

import com.smartgated.platform.presentation.dto.report.get.GetReport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartgated.platform.application.usecase.report.ReportUseCase;
import com.smartgated.platform.domain.model.report.Report;
import com.smartgated.platform.presentation.dto.report.adminReply.AdminReplyReportRequest;
import com.smartgated.platform.presentation.dto.report.create.request.CreateReportRequest;
import com.smartgated.platform.presentation.dto.report.create.response.CreateReportResponse;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportUseCase reportUseCase;

    public ReportController(ReportUseCase reportUseCase) {
        this.reportUseCase = reportUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateReportResponse> createReport(
            @RequestBody CreateReportRequest request) {

        CreateReportResponse response = reportUseCase.createReport(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<GetReport>> getAllReports() {
        return ResponseEntity.ok(reportUseCase.getAllReports());
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<GetReport> getReportById(
            @PathVariable UUID reportId) {

        return ResponseEntity.ok(reportUseCase.getReportById(reportId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GetReport>> getReportsByUserId(
            @PathVariable UUID userId) {

        return ResponseEntity.ok(reportUseCase.getReportsByUserId(userId));
    }

    @PatchMapping("/{reportId}")
    public ResponseEntity<GetReport> updateReport(
            @PathVariable UUID reportId,
            @RequestBody AdminReplyReportRequest request) {

        return ResponseEntity.ok(
                reportUseCase.updateReport(reportId, request)
        );
    }

    @DeleteMapping("/{reportId}")
    public ResponseEntity<Void> deleteReport(
            @PathVariable UUID reportId) {

        reportUseCase.deleteReport(reportId);
        return ResponseEntity.noContent().build();
    }
}