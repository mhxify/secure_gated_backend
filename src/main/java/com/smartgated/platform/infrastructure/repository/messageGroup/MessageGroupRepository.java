package com.smartgated.platform.infrastructure.repository.messageGroup;

import com.smartgated.platform.domain.model.messageGroup.MessageGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MessageGroupRepository extends JpaRepository<MessageGroup , UUID> {

    List<MessageGroup> findByMembers_UserId(UUID userId);


}
