package com.smartgated.platform.application.usecase.messageGroup;


import com.smartgated.platform.presentation.dto.messageGroup.create.request.CreateMessageGroupRequest;
import com.smartgated.platform.presentation.dto.messageGroup.create.response.CreateMessageGroupResponse;
import com.smartgated.platform.presentation.dto.messageGroup.get.GetMessageGroup;
import com.smartgated.platform.presentation.dto.message.get.GetMessage;

import java.util.List;
import java.util.UUID;

public interface MessageGroupUseCase {

    CreateMessageGroupResponse createGroup(CreateMessageGroupRequest request);

    void deleteGroup(UUID groupId, UUID userId);

    List<GetMessageGroup> getMyGroups(UUID userId);

    List<GetMessage> getGroupMessages(UUID groupId, UUID userId);
}
