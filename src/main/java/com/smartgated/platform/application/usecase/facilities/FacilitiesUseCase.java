package com.smartgated.platform.application.usecase.facilities;

import com.smartgated.platform.domain.enums.facility.FacilitiesStatus;
import com.smartgated.platform.presentation.dto.facilities.create.request.CreateFacilityRequest;
import com.smartgated.platform.presentation.dto.facilities.create.response.CreateFacilityResponse;
import com.smartgated.platform.presentation.dto.facilities.edit.EditFacilityRequest;
import com.smartgated.platform.presentation.dto.facilities.get.GetFacility;

import java.util.List;
import java.util.UUID;

public interface FacilitiesUseCase {

    CreateFacilityResponse createFacility(CreateFacilityRequest request) ;

    GetFacility getFacilityById(UUID facilityId);

    List<GetFacility> getFacilities();

    void deleteFacility(UUID facilityId);

    void editFacility(UUID facilityId , EditFacilityRequest request);

    void patchFacilityStatus(UUID facilityId , FacilitiesStatus status) ;
}
