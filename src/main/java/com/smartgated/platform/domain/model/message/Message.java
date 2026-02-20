package com.smartgated.platform.domain.model.message;

import com.smartgated.platform.domain.model.messageGroup.MessageGroup;
import com.smartgated.platform.domain.model.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "message")
@Getter @Setter
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID messageId;

    private String content;
    private LocalDateTime createdAt;
    private Boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="sender_id", nullable=false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="group_id", nullable=false)
    private MessageGroup messageGroup;
}