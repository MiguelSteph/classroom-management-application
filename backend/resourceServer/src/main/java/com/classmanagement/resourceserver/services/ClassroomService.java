package com.classmanagement.resourceserver.services;

import com.classmanagement.resourceserver.dtos.*;
import com.classmanagement.resourceserver.entities.Building;
import com.classmanagement.resourceserver.entities.Classroom;

import java.time.LocalDate;
import java.util.List;

public interface ClassroomService {

    List<UserDto> classroomSupervisors(int id);

    List<TimeRangeDto> getClassroomAvailability(int id, LocalDate date);

    List<ClassroomAvailabilityDto> getClassroomAllCurrentAvailability(int id);

    void shrinkClassroomAvailability(int classroomId, LocalDate fromDate, LocalDate toDate);

    boolean isDateRangeValid(int classroomId, LocalDate fromDate, LocalDate toDate);

    void createClassroomAvailabilities(NewClassroomAvailabilityDto newClassroomAvailabilityDto);

    List<ClassroomDto> getClassroomsInBuilding(int buildingId);

    Classroom addNewClassroom(ClassroomDto classroomDto);

    Classroom updateClassroom(ClassroomDto classroomDto);

}
