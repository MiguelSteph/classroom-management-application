package com.classmanagement.resourceserver.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AvailableTimeInterval {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate fromDate;

    private LocalDate toDate;

    @Enumerated(EnumType.STRING)
    private DayOfWeek weekDay;

    private LocalTime fromTime;

    private LocalTime toTime;

    private LocalDate createdDate;

    @ManyToOne
    private Classroom classroom;

    @ManyToOne
    private User supervisor;
}
