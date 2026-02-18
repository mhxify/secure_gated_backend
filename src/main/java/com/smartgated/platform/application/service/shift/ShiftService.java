package com.smartgated.platform.application.service.shift;

import com.smartgated.platform.application.usecase.shift.ShiftUseCase;
import com.smartgated.platform.domain.model.notification.Notification;
import com.smartgated.platform.domain.model.shift.Shift;
import com.smartgated.platform.domain.model.users.User;
import com.smartgated.platform.infrastructure.repository.shift.ShiftRepository;
import com.smartgated.platform.infrastructure.repository.user.UserRepository;
import com.smartgated.platform.presentation.dto.notification.get.GetNotificationDto;
import com.smartgated.platform.presentation.dto.shift.create.request.CreateShiftRequest;
import com.smartgated.platform.presentation.dto.shift.create.response.CreateShiftResponse;
import com.smartgated.platform.presentation.dto.shift.get.GetShift;
import com.smartgated.platform.presentation.dto.shift.update.UpdateShift;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ShiftService implements ShiftUseCase {

    private final ShiftRepository shiftRepository ;

    public ShiftService (
            ShiftRepository shiftRepository ,
            UserRepository userRepository
    ) {
        this.shiftRepository = shiftRepository ;
    }

    @Override
    public CreateShiftResponse createShift(CreateShiftRequest request) {
        Shift shift = new Shift() ;

        shift.setOnDate(request.getOnDate());
        shift.setCreatedAt(LocalDateTime.now());
        if (request.getEndTime().after(request.getStartTime())) {
            shift.setStartTime(request.getStartTime());
            shift.setEndTime(request.getEndTime());
        }else {
            throw new RuntimeException("The end time need to be after the start date") ;
        }

        Shift savedShift = shiftRepository.save(shift);
        CreateShiftResponse shiftResponse = new CreateShiftResponse();

        shiftResponse.setShiftId(savedShift.getShiftId());

        return shiftResponse;
    }

    @Override
    public void deleteShift(UUID shiftId) {
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found"));

        shiftRepository.deleteById(shiftId);
    }

    @Override
    public void updateShift(UUID shiftId, UpdateShift request) {
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found"));

        if (request.getEndTime().after(request.getStartTime())){
            shift.setEndTime(request.getEndTime());
            shift.setStartTime(request.getStartTime());
        } else {
            throw new RuntimeException("The end time need to be after the start date") ;
        }

        shift.setOnDate(request.getOnDate());

        shiftRepository.save(shift);
    }

    @Override
    public List<GetShift> getAllShifts() {
        List<Shift> shifts =
                shiftRepository.findAll();

        return shifts.stream()
                .map(shift -> {
                    GetShift dto = new GetShift();
                    dto.setCreatedAt(shift.getCreatedAt());
                    dto.setEndTime(shift.getEndTime());
                    dto.setOnDate(shift.getOnDate());
                    dto.setShiftId(shift.getShiftId());
                    return dto;
                })
                .toList();
    }

    @Override
    public GetShift getShiftById(UUID shiftId) {
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found"));

        GetShift getShift = new GetShift();

        getShift.setShiftId(shift.getShiftId());

        getShift.setOnDate(shift.getOnDate());

        shift.setStartTime(shift.getStartTime());

        shift.setEndTime(shift.getEndTime());

        return getShift;


    }


}
