package com.classmanagement.resourceserver.rest;

import com.classmanagement.resourceserver.dtos.ClassroomAvailabilityDto;
import com.classmanagement.resourceserver.dtos.TimeRangeDto;
import com.classmanagement.resourceserver.dtos.UserDto;
import com.classmanagement.resourceserver.services.ClassroomService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@AllArgsConstructor
public class ClassroomResource {

    private final ClassroomService classroomService;

    @GetMapping("/classrooms/{id}/supervisors")
    public List<UserDto> getClassroomSupervisors(@PathVariable int id) {
        return classroomService.classroomSupervisors(id);
    }

    @GetMapping("/classrooms/{id}/allCurrentAvailabilities")
    public List<ClassroomAvailabilityDto> getClassroomAllAvailabilities(@PathVariable int id) {
        return classroomService.getClassroomAllCurrentAvailability(id);
    }

    @GetMapping("/classrooms/{id}/availableTimeRanges")
    public List<TimeRangeDto> getClassroomAvailabilities(
            @PathVariable int id,
            @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return classroomService.getClassroomAvailability(id, date);
    }

    @PutMapping("/classrooms/{id}/shrinkAvailability")
    public void shrinkClassroomAvailability(@PathVariable(name = "id") int classroomId,
             @RequestBody Map<String, Object> requestBodyMap) {
        /*
        todo: Add extra security layer to enforce the fact that only the allowed supervisor can shrink the availabilities
         */
        LocalDate fromDate = LocalDate.parse((String) requestBodyMap.get("fromDate"), DateTimeFormatter.ISO_DATE);
        LocalDate toDate = LocalDate.parse((String) requestBodyMap.get("toDate"), DateTimeFormatter.ISO_DATE);
        classroomService.shrinkClassroomAvailability(classroomId, fromDate, toDate);
    }
}
