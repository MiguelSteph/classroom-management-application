package com.classmanagement.resourceserver.dtos;

import com.classmanagement.resourceserver.entities.Status;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookingRequestDto {
    private Long id;
    private Status status;
    private LocalDate createdDate;
    private LocalDate lastUpdatedDate;
    private String purpose;
    private String rejectionReason;
    private LocalDate bookingDate;
    private LocalTime fromTime;
    private LocalTime toTime;
    private ClassroomDto classroom;
    private BuildingDto building;
    private SiteDto site;
    private UserDto assignedTo;
    private UserDto createdBy;
}
