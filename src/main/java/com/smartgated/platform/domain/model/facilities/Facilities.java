package com.smartgated.platform.domain.model.facilities;

import com.smartgated.platform.domain.enums.facility.FacilitiesStatus;
import com.smartgated.platform.domain.model.reservation.Reservation;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "facilities")
public class Facilities {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID facilityId;

    private String name ;

    private Long capacity;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private FacilitiesStatus facilitiesStatus ;

    @ManyToOne(fetch = FetchType.LAZY)
    private Reservation reservation ;



    public Facilities(){

    }

    public Facilities(
            UUID facilityId ,
            String name ,
            Long capacity ,
            FacilitiesStatus facilitiesStatus
    ) {
        this.facilityId = facilityId ;
        this.name = name ;
        this.capacity = capacity;
        this.facilitiesStatus = facilitiesStatus;
    }

    public void setFacilitiesStatus(FacilitiesStatus facilitiesStatus) {
        this.facilitiesStatus = facilitiesStatus;
    }

    public void setFacilityId(UUID facilityId) {
        this.facilityId = facilityId;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public UUID getFacilityId() {
        return facilityId;
    }

    public Long getCapacity() {
        return capacity;
    }

    public FacilitiesStatus getFacilitiesStatus() {
        return facilitiesStatus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
