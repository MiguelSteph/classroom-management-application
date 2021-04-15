package com.classmanagement.resourceserver.dtos;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TimeIntervalDto {
    private Long id;
    private LocalDate fromDate;
    private LocalDate toDate;
    private DayOfWeek weekDay;
    private LocalTime fromTime;
    private LocalTime toTime;
    private LocalDate createdDate;
}
