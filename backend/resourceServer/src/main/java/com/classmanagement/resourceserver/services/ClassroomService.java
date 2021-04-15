package com.classmanagement.resourceserver.services;

import com.classmanagement.resourceserver.dtos.TimeIntervalDto;
import com.classmanagement.resourceserver.dtos.UserDto;

import java.time.LocalDate;
import java.util.List;

public interface ClassroomService {

    List<UserDto> classroomSupervisors(int id);

    List<TimeIntervalDto> getClassroomAvailability(int id, LocalDate date);

}
