package com.smartgated.platform.presentation.controller.message;

import com.smartgated.platform.application.usecase.message.MessageUseCase;
import com.smartgated.platform.presentation.dto.message.create.request.CreateMessageRequest;
import com.smartgated.platform.presentation.dto.message.create.response.CreateMessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageUseCase messageUseCase;

    public MessageController(MessageUseCase messageUseCase) {
        this.messageUseCase = messageUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateMessageResponse> send(@RequestBody CreateMessageRequest request) {
        return ResponseEntity.ok(messageUseCase.sendMessage(request));
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID messageId,
            @RequestParam UUID userId
    ) {
        messageUseCase.deleteMessage(messageId, userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{messageId}")
    public ResponseEntity<Void> patchContent(
            @PathVariable UUID messageId,
            @RequestParam UUID userId,
            @RequestParam String content
    ) {
        messageUseCase.patchMessage(messageId, userId, content);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{messageId}/read")
    public ResponseEntity<Void> markRead(
            @PathVariable UUID messageId,
            @RequestParam UUID userId,
            @RequestParam boolean isRead
    ) {
        messageUseCase.markAsRead(messageId, userId, isRead);
        return ResponseEntity.noContent().build();
    }
}
