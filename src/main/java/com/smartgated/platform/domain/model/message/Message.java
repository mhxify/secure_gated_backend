package com.smartgated.platform.domain.model.message;

import com.smartgated.platform.domain.model.messageGroup.MessageGroup;
import com.smartgated.platform.domain.model.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "message")
public class Message {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID messageId ;

    private String content ;

    private LocalDateTime createdAt ;

    private Boolean isRead ;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private MessageGroup messageGroup ;

    public Message() {

    }

    public Message(
            String content ,
            LocalDateTime createdAt ,
            boolean isRead ,
            User user ,
            MessageGroup messageGroup
    ) {
        this.content = content ;
        this.createdAt = createdAt ;
        this.isRead = isRead ;
        this.user = user ;
        this.messageGroup = messageGroup ;
    }


}
