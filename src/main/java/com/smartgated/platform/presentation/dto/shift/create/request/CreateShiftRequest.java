package com.smartgated.platform.presentation.dto.shift.create.request;

import java.sql.Time;
import java.util.Date;

public class CreateShiftRequest {

    private Date onDate ;

    private Time startTime ;

    private Time endTime ;

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setOnDate(Date onDate) {
        this.onDate = onDate;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public Date getOnDate() {
        return onDate;
    }
}
