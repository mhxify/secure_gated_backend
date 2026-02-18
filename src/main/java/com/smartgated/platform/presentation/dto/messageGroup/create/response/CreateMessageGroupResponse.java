package com.smartgated.platform.presentation.dto.messageGroup.create.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class CreateMessageGroupResponse {
    private UUID groupId ;
    private String groupName ;
    private String imageUrl ;
    private LocalDateTime createdAt ;
}
