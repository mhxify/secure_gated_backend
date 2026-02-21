package com.smartgated.platform.presentation.controller.messageGroup;

import com.smartgated.platform.application.service.file.FileStorageService;
import com.smartgated.platform.application.usecase.messageGroup.MessageGroupUseCase;
import com.smartgated.platform.presentation.dto.message.get.GetMessage;
import com.smartgated.platform.presentation.dto.messageGroup.create.request.CreateMessageGroupRequest;
import com.smartgated.platform.presentation.dto.messageGroup.create.response.CreateMessageGroupResponse;
import com.smartgated.platform.presentation.dto.messageGroup.get.GetMessageGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/message-groups")
public class MessageGroupController {

    private final MessageGroupUseCase messageGroupUseCase;
    private final FileStorageService fileStorageService;

    public MessageGroupController(
            MessageGroupUseCase messageGroupUseCase ,
            FileStorageService fileStorageService
    ) {
        this.messageGroupUseCase = messageGroupUseCase;
        this.fileStorageService = fileStorageService ;
    }

    @PostMapping(value = "/create", consumes = "multipart/form-data")
    public ResponseEntity<CreateMessageGroupResponse> create(
            @RequestParam UUID userId,
            @RequestParam String groupName,
            @RequestPart(required = false) MultipartFile image,
            @RequestParam(required = false) List<UUID> memberIds
    ) {
        String imageUrl = fileStorageService.saveGroupImage(image);

        CreateMessageGroupRequest req = new CreateMessageGroupRequest();
        req.setUserId(userId);
        req.setGroupName(groupName);
        req.setImageUrl(imageUrl);
        req.setMemberIds(memberIds);

        return ResponseEntity.ok(messageGroupUseCase.createGroup(req));
    }

    @GetMapping("/me")
    public ResponseEntity<List<GetMessageGroup>> myGroups(@RequestParam UUID userId) {
        return ResponseEntity.ok(messageGroupUseCase.getMyGroups(userId));
    }

    @GetMapping("/{groupId}/groupMessages")
    public ResponseEntity<List<GetMessage>> groupMessages(
            @PathVariable UUID groupId,
            @RequestParam UUID userId
    ) {
        return ResponseEntity.ok(messageGroupUseCase.getGroupMessages(groupId, userId));
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID groupId,
            @RequestParam UUID userId
    ) {
        messageGroupUseCase.deleteGroup(groupId, userId);
        return ResponseEntity.noContent().build();
    }
}
