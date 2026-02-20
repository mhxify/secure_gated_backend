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

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID groupId;

    private String groupName;
    private String imageUrl;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "messageGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "message_group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> members;

    public MessageGroup() {}
}