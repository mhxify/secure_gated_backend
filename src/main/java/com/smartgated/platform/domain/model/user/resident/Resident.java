package com.smartgated.platform.domain.model.user.resident;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.UUID;
import com.smartgated.platform.domain.model.user.User;
import com.smartgated.platform.domain.enums.user.UserRole;

@Entity
@Table(name = "residents")
public class Resident extends User {

    @Column(name = "unit_number", nullable = false)
    private String unitNumber;

    @Column(name = "moveInDate", nullable = false)
    private Date moveInDate;

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public Date getMoveInDate() {
        return moveInDate;
    }

    public void setMoveInDate(Date moveInDate) {
        this.moveInDate = moveInDate;
    }


    public Resident() {
    }

    public Resident(
        UUID userUuid, 
        String fullName, 
        String email,
        String password,
        UserRole userRole,
        String unitNumber, 
        Date moveInDate
    ) {
        super(userUuid, fullName, email, password , userRole);
        this.unitNumber = unitNumber;
        this.moveInDate = moveInDate;
    }

    
}
