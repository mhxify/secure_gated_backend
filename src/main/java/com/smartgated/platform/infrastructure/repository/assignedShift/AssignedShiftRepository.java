package com.smartgated.platform.infrastructure.repository.assignedShift;

import com.smartgated.platform.domain.model.assignedShift.AssignedShift;
import com.smartgated.platform.domain.model.shift.Shift;
import com.smartgated.platform.domain.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssignedShiftRepository extends JpaRepository<AssignedShift, UUID> {

    List<AssignedShift> findByUser(User user);

    List<AssignedShift> findByShift(Shift shift);

    Optional<AssignedShift> findByUserAndShift(User user, Shift shift);

    void deleteByUserAndShift(User user, Shift shift);

    List<AssignedShift> findByUser_UserId(UUID userId);

    // Optional: load shift eagerly to avoid Lazy problems when mapping
    @Query("""
        select a from AssignedShift a
        join fetch a.shift s
        where a.user.userId = :userId
    """)
    List<AssignedShift> findByUserIdWithShift(UUID userId);
}

