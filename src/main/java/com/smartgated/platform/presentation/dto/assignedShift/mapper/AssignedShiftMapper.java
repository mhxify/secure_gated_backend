package com.smartgated.platform.presentation.dto.assignedShift.mapper;

import com.smartgated.platform.domain.model.assignedShift.AssignedShift;
import com.smartgated.platform.domain.model.shift.Shift;
import com.smartgated.platform.presentation.dto.assignedShift.get.GetAssignedShift;

public class AssignedShiftMapper {

    public static GetAssignedShift toGetDto(AssignedShift a) {
        GetAssignedShift dto = new GetAssignedShift();
        dto.setAssignId(a.getAssignId());
        dto.setShiftId(a.getShift().getShiftId());
        dto.setOnDate(a.getShift().getOnDate());
        dto.setEndTime(a.getShift().getEndTime());
        dto.setStartTime(a.getShift().getEndTime());

        Shift s = a.getShift();
        dto.setShiftId(s.getShiftId());

        return dto;
    }
}
