package com.classmanagement.resourceserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeRangeDto implements Comparable<TimeRangeDto> {
    private LocalTime fromTime;
    private LocalTime toTime;

    @Override
    public int compareTo(TimeRangeDto o) {
        return this.toTime.compareTo(o.toTime);
    }
}
