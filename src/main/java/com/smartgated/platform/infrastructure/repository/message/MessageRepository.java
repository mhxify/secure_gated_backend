package com.smartgated.platform.infrastructure.repository.message;

import com.smartgated.platform.domain.model.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message , UUID> {

    List<Message> findByMessageGroup_GroupIdOrderByCreatedAtDesc(UUID groupId);
}