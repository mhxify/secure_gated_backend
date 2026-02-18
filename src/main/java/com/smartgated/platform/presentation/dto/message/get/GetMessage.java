package com.smartgated.platform.presentation.dto.message.get;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class GetMessage {

    private UUID messageId ;
    private String content ;
    private LocalDateTime createdAt ;
    private boolean isRead ;

    public GetMessage(
            UUID messageId ,
            String content ,
            LocalDateTime createdAt ,
            boolean isRead
    ) {
        this.messageId = messageId ;
        this.content = content ;
        this.createdAt = createdAt ;
        this.isRead = isRead ;
    }
}
