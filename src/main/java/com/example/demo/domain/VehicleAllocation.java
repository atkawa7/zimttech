package com.example.demo.domain;

import com.example.demo.dto.RequestStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "vehicle_allocation")
public class VehicleAllocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requestor_id")
    private User requestor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id")
    private User approver;

    @Enumerated(value = EnumType.STRING)
    private RequestStatus status;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate actualEndDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ended_by_id")
    private User endedBy;

    //Diff in days
    private Integer days;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;



}
