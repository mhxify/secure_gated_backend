package com.smartgated.platform.presentation.controller.assignedShift;

import com.smartgated.platform.application.usecase.assignedShift.AssignedShiftUseCase;
import com.smartgated.platform.presentation.dto.assignedShift.create.request.AssignShiftRequest;
import com.smartgated.platform.presentation.dto.assignedShift.create.response.AssignShiftResponse;
import com.smartgated.platform.presentation.dto.assignedShift.get.GetAssignedShift;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/assignedShift")
public class AssignedShiftController {

    private final AssignedShiftUseCase assignedShiftUseCase ;

    public AssignedShiftController(AssignedShiftUseCase assignedShiftUseCase) {
        this.assignedShiftUseCase = assignedShiftUseCase;
    }

    @PostMapping
    public ResponseEntity<AssignShiftResponse> assignShift(@RequestBody AssignShiftRequest request) {
        AssignShiftResponse response = assignedShiftUseCase.assignShiftToSecurityGuard(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}/{shiftId}")
    public ResponseEntity<Void> unassignShift(
            @PathVariable UUID userId,
            @PathVariable UUID shiftId
    ) {
        assignedShiftUseCase.unassignShift(userId, shiftId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/me")
    public ResponseEntity<List<GetAssignedShift>> getMyShifts(@RequestParam UUID userId) {
        List<GetAssignedShift> shifts = assignedShiftUseCase.getMyAssignedShifts(userId);
        return ResponseEntity.ok(shifts);
    }
}
