package com.smartgated.platform.infrastructure.repository.shift;

import com.smartgated.platform.domain.model.shift.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShiftRepository extends JpaRepository<Shift, UUID> {

}

