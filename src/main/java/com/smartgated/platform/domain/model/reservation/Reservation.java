package com.smartgated.platform.domain.model.reservation;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


import com.smartgated.platform.domain.enums.reservation.ReservationStatus;
import com.smartgated.platform.domain.model.facilities.Facilities;
import com.smartgated.platform.domain.model.users.User;

import jakarta.persistence.*;

@Entity
@Table(name = "reservations")
public class Reservation {
    
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID reservationId;

    private Date reservationDate;

    private Time startTime;

    private Time endTime;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private Long numberOfGuests;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "reservation")
    List<Facilities> facilities ;

    public List<Facilities> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<Facilities> facilities) {
        this.facilities = facilities;
    }

    public UUID getReservationId() {
        return reservationId;
    }

    public void setReservationId(UUID reservationId) {
        this.reservationId = reservationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }


    public Time getEndTime() {
        return endTime;
    }


    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }


    public Long getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(Long numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public Reservation() {
    }

    public Reservation(Date reservationDate, Time startTime, Time endTime, LocalDateTime createdAt,
            ReservationStatus status, Long numberOfGuests) {
        this.reservationDate = reservationDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createdAt = createdAt;
        this.status = status;
        this.numberOfGuests = numberOfGuests;
    }


    
}
