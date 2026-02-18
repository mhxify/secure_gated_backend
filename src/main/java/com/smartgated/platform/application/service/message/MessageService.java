package com.smartgated.platform.application.service.message;

import com.smartgated.platform.application.usecase.message.MessageUseCase;
import com.smartgated.platform.domain.model.message.Message;
import com.smartgated.platform.domain.model.messageGroup.MessageGroup;
import com.smartgated.platform.domain.model.users.User;
import com.smartgated.platform.infrastructure.repository.message.MessageRepository;
import com.smartgated.platform.infrastructure.repository.messageGroup.MessageGroupRepository;
import com.smartgated.platform.infrastructure.repository.user.UserRepository;
import com.smartgated.platform.presentation.dto.message.create.request.CreateMessageRequest;
import com.smartgated.platform.presentation.dto.message.create.response.CreateMessageResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class MessageService implements MessageUseCase {

    private final MessageRepository messageRepository;
    private final MessageGroupRepository messageGroupRepository;
    private final UserRepository userRepository;

    public MessageService(
            MessageRepository messageRepository,
            MessageGroupRepository messageGroupRepository,
            UserRepository userRepository
    ) {
        this.messageRepository = messageRepository;
        this.messageGroupRepository = messageGroupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CreateMessageResponse sendMessage(CreateMessageRequest request) {

        MessageGroup group = messageGroupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new RuntimeException("Message group not found"));

        if (!group.getUser().getUserId().equals(request.getUserId())) {
            throw new RuntimeException("Not allowed: this group is not yours");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Message m = new Message();
        m.setContent(request.getContent());
        m.setCreatedAt(LocalDateTime.now());
        m.setIsRead(false);
        m.setUser(user);
        m.setMessageGroup(group);

        Message saved = messageRepository.save(m);
        CreateMessageResponse response = new CreateMessageResponse();

        response.setMessageId(saved.getMessageId());

        return response;
    }

    @Override
    public void deleteMessage(UUID messageId, UUID userId) {
        Message m = messageRepository.findByMessageIdAndUser_UserId(messageId, userId)
                .orElseThrow(() -> new RuntimeException("Message not found or not yours"));

        messageRepository.delete(m);
    }

    @Override
    public void patchMessage(UUID messageId, UUID userId, String content) {
        Message m = messageRepository.findByMessageIdAndUser_UserId(messageId, userId)
                .orElseThrow(() -> new RuntimeException("Message not found or not yours"));

        m.setContent(content);
        messageRepository.save(m);
    }

    @Override
    public void markAsRead(UUID messageId, UUID userId, boolean isRead) {
        Message m = messageRepository.findByMessageIdAndUser_UserId(messageId, userId)
                .orElseThrow(() -> new RuntimeException("Message not found or not yours"));

        m.setIsRead(isRead);
        messageRepository.save(m);
    }
}
