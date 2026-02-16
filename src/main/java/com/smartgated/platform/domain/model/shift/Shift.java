package com.smartgated.platform.domain.model.shift;

import com.smartgated.platform.domain.model.users.User;
import jakarta.persistence.*;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "shift")
public class Shift {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID shiftId;

    private Date onDate ;

    private Time startTime ;

    private Time endTime ;

    private LocalDateTime createdAt ;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user ;

    public Shift(){

    }

    public Shift(
            UUID shiftId ,
            Date onDate ,
            Time startTime ,
            Time endTime ,
            LocalDateTime createdAt
    ){
        this.shiftId = shiftId ;
        this.onDate = onDate;
        this.startTime = startTime ;
        this.endTime = endTime ;
        this.createdAt = createdAt ;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Date getOnDate() {
        return onDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Time getEndTime() {
        return endTime;
    }

    public Time getStartTime() {
        return startTime;
    }

    public UUID getShiftId() {
        return shiftId;
    }

    public User getUser() {
        return user;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public void setShiftId(UUID shiftId) {
        this.shiftId = shiftId;
    }

    public void setOnDate(Date onDate) {
        this.onDate = onDate;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
