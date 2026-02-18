package com.smartgated.platform.infrastructure.repository.message;

import com.smartgated.platform.domain.model.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message , UUID> {
    Optional<Message> findByMessageIdAndUser_UserId(UUID messageId, UUID userId);

    @Query("""
        select m from Message m
        join fetch m.messageGroup g
        where g.groupId = :groupId and g.user.userId = :userId
        order by m.createdAt desc
    """)
    List<Message> findGroupMessages(UUID groupId, UUID userId);
}