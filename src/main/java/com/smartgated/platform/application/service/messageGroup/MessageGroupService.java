package com.smartgated.platform.application.service.messageGroup;

import com.smartgated.platform.application.usecase.messageGroup.MessageGroupUseCase;
import com.smartgated.platform.domain.model.messageGroup.MessageGroup;
import com.smartgated.platform.domain.model.users.User;
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
import java.util.ArrayList;
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

        User owner = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        MessageGroup g = new MessageGroup();
        g.setGroupName(request.getGroupName());
        g.setImageUrl(request.getImageUrl());
        g.setCreatedAt(LocalDateTime.now());

        g.setOwner(owner);

        List<User> members = new ArrayList<>();

        members.add(owner);

        if (request.getMemberIds() != null) {
            List<User> otherMembers = userRepository.findAllById(request.getMemberIds());
            for (User u : otherMembers) {
                if (members.stream().noneMatch(m -> m.getUserId().equals(u.getUserId()))) {
                    members.add(u);
                }
            }
        }

        g.setMembers(members);

        MessageGroup saved = messageGroupRepository.save(g);

        CreateMessageGroupResponse response = new CreateMessageGroupResponse();
        response.setGroupId(saved.getGroupId());
        response.setGroupName(saved.getGroupName());
        response.setImageUrl(saved.getImageUrl());
        response.setCreatedAt(saved.getCreatedAt());
        return response;
    }

    @Override
    public void deleteGroup(UUID groupId, UUID userId) {
        MessageGroup g = messageGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        if (!g.getOwner().getUserId().equals(userId)) {
            throw new RuntimeException("Not allowed: only owner can delete group");
        }

        messageGroupRepository.delete(g);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetMessageGroup> getMyGroups(UUID userId) {

        return messageGroupRepository.findByMembers_UserId(userId)
                .stream()
                .map(g -> new GetMessageGroup(g.getGroupId(), g.getGroupName(), g.getImageUrl(), g.getCreatedAt()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetMessage> getGroupMessages(UUID groupId, UUID userId) {

        MessageGroup g = messageGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        boolean isMember = g.getMembers() != null && g.getMembers().stream()
                .anyMatch(u -> u.getUserId().equals(userId));

        if (!isMember) {
            throw new RuntimeException("Not allowed: you are not a member of this group");
        }

        return messageRepository.findByMessageGroup_GroupIdOrderByCreatedAtDesc(groupId)
                .stream()
                .map(m -> new GetMessage(m.getMessageId(), m.getContent(), m.getCreatedAt(), Boolean.TRUE.equals(m.getIsRead())))
                .toList();
    }
}