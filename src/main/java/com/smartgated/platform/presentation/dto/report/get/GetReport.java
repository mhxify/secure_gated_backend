package com.smartgated.platform.presentation.dto.report.get;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class GetReport {

    private String content;

    private LocalDateTime sentAt;

    private String imageUrl;

    private UUID userId;

    private LocalDateTime repliedAt ;

    private String adminReply ;

}
