package com.smartgated.platform.application.service.log;

import com.smartgated.platform.application.usecase.log.LogUseCase;
import com.smartgated.platform.domain.model.log.Log;
import com.smartgated.platform.domain.model.notification.Notification;
import com.smartgated.platform.infrastructure.repository.log.LogRepository;
import com.smartgated.platform.presentation.dto.log.create.request.CreateLogRequest;
import com.smartgated.platform.presentation.dto.log.create.response.CreateLogResponse;
import com.smartgated.platform.presentation.dto.log.get.GetLog;
import com.smartgated.platform.presentation.dto.notification.get.GetNotificationDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogService implements LogUseCase {

    private final LogRepository logRepository ;

    public LogService(
            LogRepository logRepository
    ) {
        this.logRepository = logRepository ;
    }

    @Override
    public CreateLogResponse createLog(CreateLogRequest request ) {
        Log log = new Log() ;

        log.setPersonFullName(request.getPersonFullName());
        log.setLogTime(LocalDateTime.now());
        log.setLogState("Enter"); // For the moment
        log.setUserRole(request.getUserRole());

        Log savedLog = logRepository.save(log);
        CreateLogResponse response = new CreateLogResponse();

        response.setLogId(savedLog.getLogId());

        return response ;
    }

    @Override
    public List<GetLog> getAllLogs() {
        List<Log> logs =
                logRepository.findAll();

        return logs.stream()
                .map(log -> {
                    GetLog dto = new GetLog();
                    dto.setLogId(log.getLogId());
                    dto.setLogTime(log.getLogTime());
                    dto.setUserRole(log.getUserRole());
                    dto.setPersonFullName(log.getPersonFullName());
                    dto.setLogState(log.getLogState());
                    return dto;
                })
                .toList();
    }
}
