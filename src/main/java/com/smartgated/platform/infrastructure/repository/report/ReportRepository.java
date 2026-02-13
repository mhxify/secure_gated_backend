package com.smartgated.platform.infrastructure.repository.report;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartgated.platform.domain.model.report.Report;

public interface ReportRepository extends JpaRepository<Report, UUID> {

    List<Report> findByUser_UserId(UUID userId);    
}
