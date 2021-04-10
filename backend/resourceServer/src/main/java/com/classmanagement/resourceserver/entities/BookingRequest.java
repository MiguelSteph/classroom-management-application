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
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate createdDate;

    private LocalDate lastUpdatedDate;

    private String rejectionReason;

    private LocalDate bookingDate;

    private LocalTime fromTime;

    private LocalTime toTime;

    @ManyToOne
    @Column(name = "classroomId")
    private Classroom classroom;

    @ManyToOne
    @Column(name = "assignedTo")
    private User assignedTo;

    @ManyToOne
    @Column(name = "createdBy")
    private User createdBy;
}

