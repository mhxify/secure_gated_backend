package com.smartgated.platform.domain.model.messageGroup;

import com.smartgated.platform.domain.model.message.Message;
import com.smartgated.platform.domain.model.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "message_groups")
public class MessageGroup {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID groupId ;

    private String groupName ;

    private String imageUrl ;

    private LocalDateTime createdAt ;

    @OneToMany(mappedBy="messageGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;

    @ManyToOne
    private User user ;


    public MessageGroup() {

    }

    public MessageGroup(
            String groupName ,
            String imageUrl ,
            LocalDateTime createdAt ,
            List<Message> messages ,
            User user
    ) {
        this.createdAt = createdAt ;
        this.imageUrl = imageUrl ;
        this.messages = messages ;
        this.groupName = groupName ;
        this.user = user ;
    }

}
