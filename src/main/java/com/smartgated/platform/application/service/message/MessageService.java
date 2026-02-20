package com.smartgated.platform.application.service.message;

import com.smartgated.platform.application.usecase.message.MessageUseCase;
import com.smartgated.platform.domain.model.message.Message;
import com.smartgated.platform.domain.model.messageGroup.MessageGroup;
import com.smartgated.platform.domain.model.notification.Notification;
import com.smartgated.platform.domain.model.users.User;
import com.smartgated.platform.infrastructure.repository.message.MessageRepository;
import com.smartgated.platform.infrastructure.repository.messageGroup.MessageGroupRepository;
import com.smartgated.platform.infrastructure.repository.notification.NotificationRepository;
import com.smartgated.platform.infrastructure.repository.user.UserRepository;
import com.smartgated.platform.presentation.dto.message.create.request.CreateMessageRequest;
import com.smartgated.platform.presentation.dto.message.create.response.CreateMessageResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class MessageService implements MessageUseCase {

    private final MessageRepository messageRepository;
    private final MessageGroupRepository messageGroupRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    public MessageService(
            MessageRepository messageRepository,
            MessageGroupRepository messageGroupRepository,
            UserRepository userRepository,
            NotificationRepository notificationRepository
    ) {
        this.messageRepository = messageRepository;
        this.messageGroupRepository = messageGroupRepository;
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public CreateMessageResponse sendMessage(CreateMessageRequest request) {

        MessageGroup group = messageGroupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new RuntimeException("Message group not found"));

        User sender = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean isMember = group.getMembers() != null && group.getMembers().stream()
                .anyMatch(u -> u.getUserId().equals(sender.getUserId()));

        if (!isMember) {
            throw new RuntimeException("Not allowed: you are not a member of this group");
        }

        Message m = new Message();
        m.setContent(request.getContent());
        m.setCreatedAt(LocalDateTime.now());
        m.setIsRead(false);

        m.setSender(sender);
        // if you kept old name:
        // m.setUser(sender);

        m.setMessageGroup(group);

        Message saved = messageRepository.save(m);

        notifyGroupMembers(group, sender, request.getContent());

        CreateMessageResponse response = new CreateMessageResponse();
        response.setMessageId(saved.getMessageId());
        return response;
    }

    @Override
    public void deleteMessage(UUID messageId, UUID userId) {
        Message m = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        UUID senderId = m.getSender().getUserId();
        if (!senderId.equals(userId)) {
            throw new RuntimeException("Not allowed: you can delete only your messages");
        }

        messageRepository.delete(m);
    }

    @Override
    public void patchMessage(UUID messageId, UUID userId, String content) {
        Message m = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        UUID senderId = m.getSender().getUserId(); // change if needed
        if (!senderId.equals(userId)) {
            throw new RuntimeException("Not allowed: you can edit only your messages");
        }

        m.setContent(content);
        messageRepository.save(m);
    }

    @Override
    public void markAsRead(UUID messageId, UUID userId, boolean isRead) {
        Message m = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        MessageGroup group = m.getMessageGroup();

        boolean isMember = group.getMembers() != null && group.getMembers().stream()
                .anyMatch(u -> u.getUserId().equals(userId));

        if (!isMember) {
            throw new RuntimeException("Not allowed: not a group member");
        }

        m.setIsRead(isRead);
        messageRepository.save(m);
    }

    private void notifyGroupMembers(MessageGroup group, User sender, String messageContent) {

        if (group.getMembers() == null) return;

        String preview = messageContent == null ? "" : messageContent.trim();
        if (preview.length() > 60) preview = preview.substring(0, 60) + "...";

        String content = sender.getFullname() + " sent a message in '" + group.getGroupName() + "': " + preview;

        List<Notification> notifications = group.getMembers().stream()
                .filter(u -> !u.getUserId().equals(sender.getUserId()))
                .map(member -> {
                    Notification n = new Notification();
                    n.setUser(member);
                    n.setCreatedAt(LocalDateTime.now());
                    n.setRead(false);
                    n.setContent(content);
                    return n;
                })
                .toList();

        if (!notifications.isEmpty()) {
            notificationRepository.saveAll(notifications);
        }
    }
}
