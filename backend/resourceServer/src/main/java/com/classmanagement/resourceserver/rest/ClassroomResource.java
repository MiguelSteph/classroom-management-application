package com.classmanagement.resourceserver.rest;

import com.classmanagement.resourceserver.dtos.UserDto;
import com.classmanagement.resourceserver.services.ClassroomService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ClassroomResource {

    private final ClassroomService classroomService;

    @GetMapping("/classrooms/{id}/supervisors")
    public List<UserDto> getClassroomSupervisors(@PathVariable int id) {
        return classroomService.classroomSupervisors(id);
    }

}
