package com.smartgated.platform.application.usecase.log;

import com.smartgated.platform.presentation.dto.log.create.request.CreateLogRequest;
import com.smartgated.platform.presentation.dto.log.create.response.CreateLogResponse;
import com.smartgated.platform.presentation.dto.log.get.GetLog;

import java.util.List;

public interface LogUseCase {

    CreateLogResponse createLog(CreateLogRequest request ) ;

    List<GetLog> getAllLogs() ;
}
