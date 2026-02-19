package com.smartgated.platform.presentation.dto.log.create.request;

import com.smartgated.platform.domain.enums.user.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateLogRequest {

    private String personFullName ;

    private UserRole userRole ;

    private String logState ;

    private LocalDateTime logTime ;


}
