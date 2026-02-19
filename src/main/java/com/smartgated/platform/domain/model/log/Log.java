package com.smartgated.platform.domain.model.log;

import com.smartgated.platform.domain.enums.user.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "logs")
public class Log {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID logId;

    private String personFullName ;

    private UserRole userRole ;

    private String logState ;

    private LocalDateTime logTime ;

    public Log(){}

    public Log(
            UUID logId ,
            String personFullName ,
            UserRole userRole ,
            String logState
    ) {
        this.logId = logId ;
        this.personFullName = personFullName ;
        this.userRole = userRole ;
        this.logState = logState ;
    }

}
