package com.smartgated.platform.domain.model.assignedShift;

import com.smartgated.platform.domain.model.shift.Shift;
import com.smartgated.platform.domain.model.users.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "assigned_shifts",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "shift_id"})
        }
)
public class AssignedShift {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID assignId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shift_id", nullable = false)
    private Shift shift;

    @Column(nullable = false)
    private LocalDateTime assignedAt ;

    public AssignedShift(){

    }

    public AssignedShift(
            UUID assignId ,
            User user ,
            Shift shift ,
            LocalDateTime assignedAt
    ) {
        this.assignId = assignId ;
        this.user = user ;
        this.shift = shift ;
        this.assignedAt = assignedAt ;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Shift getShift() {
        return shift;
    }

    public UUID getAssignId() {
        return assignId;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    public void setAssignId(UUID assignId) {
        this.assignId = assignId;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }


}
