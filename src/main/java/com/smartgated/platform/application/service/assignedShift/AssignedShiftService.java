package com.smartgated.platform.application.service.assignedShift;

import com.smartgated.platform.application.usecase.assignedShift.AssignedShiftUseCase;
import com.smartgated.platform.domain.model.assignedShift.AssignedShift;
import com.smartgated.platform.domain.model.shift.Shift;
import com.smartgated.platform.domain.model.users.User;
import com.smartgated.platform.infrastructure.repository.assignedShift.AssignedShiftRepository;
import com.smartgated.platform.infrastructure.repository.shift.ShiftRepository;
import com.smartgated.platform.infrastructure.repository.user.UserRepository;
import com.smartgated.platform.presentation.dto.assignedShift.create.request.AssignShiftRequest;
import com.smartgated.platform.presentation.dto.assignedShift.create.response.AssignShiftResponse;
import com.smartgated.platform.presentation.dto.assignedShift.get.GetAssignedShift;
import com.smartgated.platform.presentation.dto.assignedShift.mapper.AssignedShiftMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AssignedShiftService implements AssignedShiftUseCase {

    private final AssignedShiftRepository assignedShiftRepository;
    private final UserRepository userRepository;
    private final ShiftRepository shiftRepository;

    public AssignedShiftService(
            AssignedShiftRepository assignedShiftRepository,
            UserRepository userRepository,
            ShiftRepository shiftRepository
    ) {
        this.assignedShiftRepository = assignedShiftRepository;
        this.userRepository = userRepository;
        this.shiftRepository = shiftRepository;
    }

    @Override
    public AssignShiftResponse assignShiftToSecurityGuard(AssignShiftRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Shift shift = shiftRepository.findById(request.getShiftId())
                .orElseThrow(() -> new RuntimeException("Shift not found"));

        assignedShiftRepository.findByUserAndShift(user, shift)
                .ifPresent(a -> { throw new RuntimeException("Shift already assigned to this user"); });

        AssignedShift assignedShift = new AssignedShift();
        assignedShift.setUser(user);
        assignedShift.setShift(shift);
        assignedShift.setAssignedAt(LocalDateTime.now());

        AssignedShift saved = assignedShiftRepository.save(assignedShift);

        AssignShiftResponse response = new AssignShiftResponse();
        response.setAssignId(saved.getAssignId());
        return response ;
    }

    @Override
    public void unassignShift(UUID userId, UUID shiftId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found"));

        assignedShiftRepository.findByUserAndShift(user, shift)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        assignedShiftRepository.deleteByUserAndShift(user, shift);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetAssignedShift> getMyAssignedShifts(UUID userId) {

        List<AssignedShift> list = assignedShiftRepository.findByUserIdWithShift(userId);

        return list.stream()
                .map(AssignedShiftMapper::toGetDto)
                .toList();
    }
}

