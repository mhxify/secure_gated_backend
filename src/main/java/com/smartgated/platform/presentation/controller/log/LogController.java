package com.smartgated.platform.presentation.controller.log;

import com.smartgated.platform.application.usecase.log.LogUseCase;
import com.smartgated.platform.presentation.dto.log.create.request.CreateLogRequest;
import com.smartgated.platform.presentation.dto.log.create.response.CreateLogResponse;
import com.smartgated.platform.presentation.dto.log.get.GetLog;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogController {

    private final LogUseCase logUseCase ;

    public LogController(
            LogUseCase logUseCase
    ) {
        this.logUseCase = logUseCase ;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateLogResponse> createLog(@RequestBody CreateLogRequest request) {
        CreateLogResponse response = logUseCase.createLog(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<GetLog>> getAllLogs() {
        return ResponseEntity.ok(logUseCase.getAllLogs());
    }

}
