package com.smartgated.platform.presentation.dto.message.create.request;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class CreateMessageRequest {

    private UUID groupId ;
    private UUID userId ;
    private String content ;

}
