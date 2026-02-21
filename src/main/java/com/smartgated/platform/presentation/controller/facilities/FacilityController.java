package com.smartgated.platform.presentation.controller.facilities;

import com.smartgated.platform.application.service.file.FileStorageService;
import com.smartgated.platform.application.usecase.facilities.FacilitiesUseCase;
import com.smartgated.platform.domain.enums.facility.FacilitiesStatus;
import com.smartgated.platform.presentation.dto.facilities.create.request.CreateFacilityRequest;
import com.smartgated.platform.presentation.dto.facilities.create.response.CreateFacilityResponse;
import com.smartgated.platform.presentation.dto.facilities.edit.EditFacilityRequest;
import com.smartgated.platform.presentation.dto.facilities.get.GetFacility;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/facilities")
public class FacilityController {

    private final FacilitiesUseCase facilitiesUseCase;
    private final FileStorageService fileStorageService;


    public FacilityController(
            FacilitiesUseCase facilitiesUseCase ,
            FileStorageService fileStorageService
    ) {
        this.facilitiesUseCase = facilitiesUseCase;
        this.fileStorageService = fileStorageService ;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<CreateFacilityResponse> createFacility(
            @RequestParam String name,
            @RequestParam Long capacity,
            @RequestPart(required = false) MultipartFile image
    ) {
        String imageUrl = fileStorageService.saveFacilityImage(image);

        CreateFacilityRequest req = new CreateFacilityRequest();
        req.setName(name);
        req.setCapacity(capacity);
        req.setImageUrl(imageUrl);

        return ResponseEntity.ok(facilitiesUseCase.createFacility(req));
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

    @PatchMapping(
            value = "/{facilityId}", consumes = "multipart/form-data"
    )
    public ResponseEntity<Void> editFacility(
            @PathVariable UUID facilityId,
            @RequestParam String name,
            @RequestParam Long capacity,
            @RequestPart(required = false) MultipartFile image
    ) {
        String imageUrl = (image != null && !image.isEmpty())
                ? fileStorageService.saveFacilityImage(image)
                : null;

        EditFacilityRequest req = new EditFacilityRequest();
        req.setName(name);
        req.setCapacity(capacity);

        if (imageUrl != null) {
            req.setImageUrl(imageUrl);
        }

        facilitiesUseCase.editFacility(facilityId, req);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{facilityId}/status")
    public ResponseEntity<Void> patchFacilityStatus(
            @PathVariable UUID facilityId,
            @RequestParam FacilitiesStatus status
    ) {

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
