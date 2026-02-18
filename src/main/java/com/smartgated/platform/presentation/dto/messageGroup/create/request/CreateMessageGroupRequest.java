package com.smartgated.platform.presentation.dto.messageGroup.create.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateMessageGroupRequest {
    private UUID userId ;
    private String groupName ;
    private String imageUrl ;
}
