package com.classmanagement.resourceserver.services;

import com.classmanagement.resourceserver.dtos.ClassroomAvailabilityDto;
import com.classmanagement.resourceserver.dtos.TimeRangeDto;
import com.classmanagement.resourceserver.dtos.UserDto;

import java.time.LocalDate;
import java.util.List;

public interface ClassroomService {

    List<UserDto> classroomSupervisors(int id);

    List<TimeRangeDto> getClassroomAvailability(int id, LocalDate date);

    List<ClassroomAvailabilityDto> getClassroomAllCurrentAvailability(int id);

}
