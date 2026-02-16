package com.smartgated.platform.infrastructure.repository.facilities;

import com.smartgated.platform.domain.model.facilities.Facilities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface FacilitiesRepository extends JpaRepository<Facilities , UUID> {
}
