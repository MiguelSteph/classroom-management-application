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
public class TimeInterval {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private LocalDate fromDate;

    private LocalDate toDate;

    @Enumerated(EnumType.STRING)
    private DayOfWeek weekDay;

    private LocalTime fromTime;

    private LocalTime toTime;

    @ManyToOne
    @Column(name = "availabilityId")
    private Availability availability;
}
