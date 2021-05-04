package com.classmanagement.resourceserver.dtos;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
public class ClassroomAvailabilityDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private Map<DayOfWeek, List<TimeRangeDto>> timeRangeByDayOfWeek;
}
