package com.classmanagement.resourceserver.dtos;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class CreateBookingDto {
    private String username;
    private String purpose;
    private int classroomId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    private int startHour;
    private int endHour;
    private Long supervisorId;
}
