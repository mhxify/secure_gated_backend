package com.smartgated.platform.application.usecase.shift;

import java.util.List;
import java.util.UUID;

import com.smartgated.platform.presentation.dto.shift.create.request.CreateShiftRequest;
import com.smartgated.platform.presentation.dto.shift.create.response.CreateShiftResponse;
import com.smartgated.platform.presentation.dto.shift.get.GetShift;
import com.smartgated.platform.presentation.dto.shift.update.UpdateShift;

public interface ShiftUseCase {

    CreateShiftResponse createShift(CreateShiftRequest request);

    void updateShift(UUID shiftId, UpdateShift request);

    void deleteShift(UUID shiftId);

    List<GetShift> getAllShifts();

    GetShift getShiftById(UUID shiftId);
}
