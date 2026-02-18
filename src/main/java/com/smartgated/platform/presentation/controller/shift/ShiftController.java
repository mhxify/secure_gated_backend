package com.smartgated.platform.presentation.controller.shift;

import com.smartgated.platform.application.usecase.shift.ShiftUseCase;
import com.smartgated.platform.presentation.dto.shift.create.request.CreateShiftRequest;
import com.smartgated.platform.presentation.dto.shift.create.response.CreateShiftResponse;
import com.smartgated.platform.presentation.dto.shift.get.GetShift;
import com.smartgated.platform.presentation.dto.shift.update.UpdateShift;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/shift")
public class ShiftController {

    private final ShiftUseCase shiftUseCase ;

    public ShiftController(
            ShiftUseCase shiftUseCase
    ) {
        this.shiftUseCase = shiftUseCase ;
    }


    @PostMapping
    public ResponseEntity<CreateShiftResponse> createShift(@RequestBody CreateShiftRequest request) {
        CreateShiftResponse response = shiftUseCase.createShift(request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{shiftId}")
    public ResponseEntity<Void> updateShift(
            @PathVariable UUID shiftId,
            @RequestBody UpdateShift request
    ) {
        shiftUseCase.updateShift(shiftId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{shiftId}")
    public ResponseEntity<Void> deleteShift(@PathVariable UUID shiftId) {
        shiftUseCase.deleteShift(shiftId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<GetShift>> getAllShifts() {
        return ResponseEntity.ok(shiftUseCase.getAllShifts());
    }


    @GetMapping("/{shiftId}")
    public ResponseEntity<GetShift> getShiftById(@PathVariable UUID shiftId) {
        return ResponseEntity.ok(shiftUseCase.getShiftById(shiftId));
    }


}
