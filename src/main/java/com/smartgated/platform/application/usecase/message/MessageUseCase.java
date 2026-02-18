package com.smartgated.platform.application.usecase.message;

import com.smartgated.platform.presentation.dto.message.create.request.CreateMessageRequest;
import com.smartgated.platform.presentation.dto.message.create.response.CreateMessageResponse;

import java.util.UUID;

public interface MessageUseCase {

    CreateMessageResponse sendMessage(CreateMessageRequest request);

    void deleteMessage(UUID messageId, UUID userId);

    void patchMessage(UUID messageId, UUID userId, String content);

    void markAsRead(UUID messageId, UUID userId, boolean isRead);
}
