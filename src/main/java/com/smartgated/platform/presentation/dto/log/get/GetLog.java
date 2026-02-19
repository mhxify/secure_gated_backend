package com.smartgated.platform.presentation.dto.log.get;

import com.smartgated.platform.domain.enums.user.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class GetLog {

    private UUID logId;

    private String personFullName ;

    private UserRole userRole ;

    private String logState ;

    private LocalDateTime logTime ;
}
