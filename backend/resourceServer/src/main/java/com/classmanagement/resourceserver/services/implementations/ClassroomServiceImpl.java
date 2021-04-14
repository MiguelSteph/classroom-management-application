package com.classmanagement.resourceserver.services.implementations;

import com.classmanagement.resourceserver.dtos.UserDto;
import com.classmanagement.resourceserver.entities.Classroom;
import com.classmanagement.resourceserver.entities.ClassroomSupervisor;
import com.classmanagement.resourceserver.repositories.ClassroomRepository;
import com.classmanagement.resourceserver.services.ClassroomService;
import com.classmanagement.resourceserver.util.mappers.DtoMapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;

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
