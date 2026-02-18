package com.smartgated.platform.presentation.dto.messageGroup.get;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class GetMessageGroup {
    private UUID groupId;
    private String groupName;
    private String imageUrl ;
    private LocalDateTime createdAt ;

    public GetMessageGroup(
            UUID groupId ,
            String groupName ,
            String imageUrl ,
            LocalDateTime createdAt
    ) {
        this.groupId = groupId ;
        this.groupName = groupName ;
        this.imageUrl = imageUrl ;
        this.createdAt = createdAt ;
    }
}
