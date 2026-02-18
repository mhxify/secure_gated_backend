package com.smartgated.platform.application.usecase.assignedShift;

import com.smartgated.platform.presentation.dto.assignedShift.create.request.AssignShiftRequest;
import com.smartgated.platform.presentation.dto.assignedShift.create.response.AssignShiftResponse;
import com.smartgated.platform.presentation.dto.assignedShift.get.GetAssignedShift;

import java.util.List;
import java.util.UUID;

public interface AssignedShiftUseCase {

    AssignShiftResponse assignShiftToSecurityGuard(AssignShiftRequest request);

    void unassignShift(UUID userId, UUID shiftId);

    List<GetAssignedShift> getMyAssignedShifts(UUID userId);


}
