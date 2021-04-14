package com.classmanagement.resourceserver.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BookingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate createdDate;

    private LocalDate lastUpdatedDate;

    private String purpose;

    private String rejectionReason;

    private LocalDate bookingDate;

    private LocalTime fromTime;

    private LocalTime toTime;

    @ManyToOne
    private Classroom classroom;

    @ManyToOne
    private User assignedTo;

    @ManyToOne
    private User createdBy;
}

