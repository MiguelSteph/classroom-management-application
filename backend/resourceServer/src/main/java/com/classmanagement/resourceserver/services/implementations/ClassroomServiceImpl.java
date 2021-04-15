package com.classmanagement.resourceserver.services.implementations;

import com.classmanagement.resourceserver.dtos.TimeIntervalDto;
import com.classmanagement.resourceserver.dtos.UserDto;
import com.classmanagement.resourceserver.entities.AvailableTimeInterval;
import com.classmanagement.resourceserver.entities.Classroom;
import com.classmanagement.resourceserver.entities.ClassroomSupervisor;
import com.classmanagement.resourceserver.repositories.AvailableTimeIntervalRepository;
import com.classmanagement.resourceserver.repositories.ClassroomRepository;
import com.classmanagement.resourceserver.services.ClassroomService;
import com.classmanagement.resourceserver.util.mappers.DtoMapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final AvailableTimeIntervalRepository availableTimeIntervalRepository;

    @Override
    public List<TimeIntervalDto> getClassroomAvailability(int id, LocalDate date) {
        String weekDay = date.getDayOfWeek().toString();
        List<AvailableTimeInterval> availableTimeIntervals =
                availableTimeIntervalRepository.getClassroomAvailabilityTimeInterval(id, date, weekDay);

        return availableTimeIntervals.stream()
                .map(DtoMapperUtil::convertToTimeIntervalDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> classroomSupervisors(int id) {
        Optional<Classroom> classroomOptional = classroomRepository.findById(id);
        if (classroomOptional.isEmpty()) return Collections.emptyList();
        Classroom classroom = classroomOptional.get();
        return classroom.getClassroomSupervisors()
                .stream()
                .map(ClassroomSupervisor::getSupervisor)
                .map(DtoMapperUtil::convertToUserDto)
                .collect(Collectors.toList());
    }
}
