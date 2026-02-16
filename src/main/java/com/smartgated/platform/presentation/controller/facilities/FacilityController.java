package com.smartgated.platform.presentation.controller.facilities;

import com.smartgated.platform.application.usecase.facilities.FacilitiesUseCase;
import com.smartgated.platform.domain.enums.facility.FacilitiesStatus;
import com.smartgated.platform.presentation.dto.facilities.create.request.CreateFacilityRequest;
import com.smartgated.platform.presentation.dto.facilities.create.response.CreateFacilityResponse;
import com.smartgated.platform.presentation.dto.facilities.edit.EditFacilityRequest;
import com.smartgated.platform.presentation.dto.facilities.get.GetFacility;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/facilities")
public class FacilityController {

    private final FacilitiesUseCase facilitiesUseCase;

    public FacilityController(FacilitiesUseCase facilitiesUseCase) {
        this.facilitiesUseCase = facilitiesUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateFacilityResponse> createFacility(
            @RequestBody CreateFacilityRequest request) {

        CreateFacilityResponse response = facilitiesUseCase.createFacility(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{facilityId}")
    public ResponseEntity<GetFacility> getFacilityById(
            @PathVariable UUID facilityId) {

        GetFacility facility = facilitiesUseCase.getFacilityById(facilityId);
        return ResponseEntity.ok(facility);
    }

    @GetMapping
    public ResponseEntity<List<GetFacility>> getFacilities() {

        List<GetFacility> facilities = facilitiesUseCase.getFacilities();
        return ResponseEntity.ok(facilities);
    }

    @PatchMapping("/{facilityId}")
    public ResponseEntity<Void> editFacility(
            @PathVariable UUID facilityId,
            @RequestBody EditFacilityRequest request) {

        facilitiesUseCase.editFacility(facilityId, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{facilityId}/status")
    public ResponseEntity<Void> patchFacilityStatus(
            @PathVariable UUID facilityId,
            @RequestParam FacilitiesStatus status) {

        facilitiesUseCase.patchFacilityStatus(facilityId, status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{facilityId}")
    public ResponseEntity<Void> deleteFacility(
            @PathVariable UUID facilityId) {

        facilitiesUseCase.deleteFacility(facilityId);
        return ResponseEntity.noContent().build();
    }
}
