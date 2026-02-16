package com.smartgated.platform.application.service.facility;

import com.smartgated.platform.application.usecase.facilities.FacilitiesUseCase;
import com.smartgated.platform.domain.enums.facility.FacilitiesStatus;
import com.smartgated.platform.domain.model.facilities.Facilities;
import com.smartgated.platform.infrastructure.repository.facilities.FacilitiesRepository;
import com.smartgated.platform.presentation.dto.facilities.create.request.CreateFacilityRequest;
import com.smartgated.platform.presentation.dto.facilities.create.response.CreateFacilityResponse;
import com.smartgated.platform.presentation.dto.facilities.edit.EditFacilityRequest;
import com.smartgated.platform.presentation.dto.facilities.get.GetFacility;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FacilityService implements FacilitiesUseCase {

    private final FacilitiesRepository facilitiesRepository ;

    public FacilityService(
            FacilitiesRepository facilitiesRepository
    ) {
        this.facilitiesRepository = facilitiesRepository;
    }

    @Override
    public CreateFacilityResponse createFacility(CreateFacilityRequest request) {
        Facilities facilities = new Facilities();

        facilities.setName(request.getName());
        facilities.setFacilitiesStatus(FacilitiesStatus.AVAILABLE);
        facilities.setCapacity(request.getCapacity());
        facilities.setImageUrl(request.getImageUrl());

        Facilities savedFacility = facilitiesRepository.save(facilities) ;

        CreateFacilityResponse response = new CreateFacilityResponse();

        response.setFacilityId(savedFacility.getFacilityId());

        return response;
    }

    @Override
    public GetFacility getFacilityById(UUID facilityId) {
        if (!facilitiesRepository.existsById(facilityId)) {
            throw new RuntimeException("Facility not exist");
        }
        Facilities facility = facilitiesRepository.findById(facilityId)
                .orElseThrow(() -> new RuntimeException("Facility not exist"));

        GetFacility getFacility = new GetFacility();
        getFacility.setName(facility.getName());
        getFacility.setCapacity(facility.getCapacity());
        getFacility.setFacilityId(facility.getFacilityId());
        getFacility.setImageUrl(facility.getImageUrl());
        return getFacility;
    }

    @Override
    public List<GetFacility> getFacilities() {
        return List.of();
    }


    @Override
    public void deleteFacility(UUID facilityId) {
        if (!facilitiesRepository.existsById(facilityId)) {
            throw new RuntimeException("Facility not exist");
        }

        facilitiesRepository.deleteById(facilityId);

    }

    @Override
    public void editFacility(UUID facilityId, EditFacilityRequest request) {
        Facilities facility = facilitiesRepository.findById(facilityId)
                .orElseThrow(() -> new RuntimeException("Facility not found"));

        facility.setName(request.getName());
        facility.setImageUrl(request.getImageUrl());
        facility.setCapacity(request.getCapacity());

        facilitiesRepository.save(facility);
    }

    @Override
    public void patchFacilityStatus(UUID facilityId , FacilitiesStatus status) {
        Facilities facility = facilitiesRepository.findById(facilityId)
                .orElseThrow(() -> new RuntimeException("Facility not found"));

        facility.setFacilitiesStatus(status);

        facilitiesRepository.save(facility);
    }


}
