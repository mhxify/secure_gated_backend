package com.smartgated.platform.application.service.messageGroup;

import com.smartgated.platform.application.usecase.messageGroup.MessageGroupUseCase;
import com.smartgated.platform.domain.model.messageGroup.MessageGroup;
import com.smartgated.platform.infrastructure.repository.message.MessageRepository;
import com.smartgated.platform.infrastructure.repository.messageGroup.MessageGroupRepository;
import com.smartgated.platform.infrastructure.repository.user.UserRepository;
import com.smartgated.platform.presentation.dto.message.get.GetMessage;
import com.smartgated.platform.presentation.dto.messageGroup.create.request.CreateMessageGroupRequest;
import com.smartgated.platform.presentation.dto.messageGroup.create.response.CreateMessageGroupResponse;
import com.smartgated.platform.presentation.dto.messageGroup.get.GetMessageGroup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class MessageGroupService implements MessageGroupUseCase {

    private final MessageGroupRepository messageGroupRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageGroupService(
            MessageGroupRepository messageGroupRepository,
            MessageRepository messageRepository,
            UserRepository userRepository
    ) {
        this.messageGroupRepository = messageGroupRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CreateMessageGroupResponse createGroup(CreateMessageGroupRequest request) {

        var user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        MessageGroup g = new MessageGroup();
        g.setGroupName(request.getGroupName());
        g.setImageUrl(request.getImageUrl());
        g.setCreatedAt(LocalDateTime.now());
        g.setUser(user);

        MessageGroup saved = messageGroupRepository.save(g);

        CreateMessageGroupResponse response = new CreateMessageGroupResponse();

        response.setGroupId(saved.getGroupId());
        response.setGroupName(saved.getGroupName());
        response.setImageUrl(saved.getImageUrl());
        response.setCreatedAt(saved.getCreatedAt());

        return response ;
    }

    @Override
    public void deleteGroup(UUID groupId, UUID userId) {
        MessageGroup g = messageGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        if (!g.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("Not allowed: this group is not yours");
        }

        messageGroupRepository.delete(g);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetMessageGroup> getMyGroups(UUID userId) {
        return messageGroupRepository.findByUser_UserId(userId)
                .stream()
                .map(g -> new GetMessageGroup(g.getGroupId(), g.getGroupName(), g.getImageUrl(), g.getCreatedAt()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetMessage> getGroupMessages(UUID groupId, UUID userId) {
        return messageRepository.findGroupMessages(groupId, userId)
                .stream()
                .map(m -> new GetMessage(m.getMessageId(), m.getContent(), m.getCreatedAt(), Boolean.TRUE.equals(m.getIsRead())))
                .toList();
    }
}

