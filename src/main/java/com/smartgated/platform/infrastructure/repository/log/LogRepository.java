package com.smartgated.platform.infrastructure.repository.log;

import com.smartgated.platform.domain.model.log.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LogRepository extends JpaRepository<Log, UUID> {

}
