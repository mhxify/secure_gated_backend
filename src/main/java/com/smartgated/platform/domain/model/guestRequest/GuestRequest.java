package com.smartgated.platform.domain.model.guestRequest;

import com.smartgated.platform.domain.enums.guest.GuestRequestStatus;
import com.smartgated.platform.domain.model.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "guest_request")
@Getter
@Setter
public class GuestRequest {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID requestId ;

    private String guestEmail ;

    private LocalDateTime startTime ;

    private LocalDateTime endTime ;

    @Enumerated(EnumType.STRING)
    private GuestRequestStatus requestStatus;

    private LocalDateTime createdAt ;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user ;

    public GuestRequest() {}

    public GuestRequest(
            UUID requestId ,
            String guestEmail ,
            LocalDateTime startTime ,
            LocalDateTime endTime ,
            GuestRequestStatus requestStatus ,
            LocalDateTime createdAt
    ) {
        this.requestId = requestId ;
        this.guestEmail = guestEmail ;
        this.startTime = startTime ;
        this.endTime = endTime ;
        this.requestStatus = requestStatus ;
        this.createdAt = createdAt ;
    }
}
