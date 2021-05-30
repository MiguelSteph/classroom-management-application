package com.classmanagement.resourceserver.rest;

import com.classmanagement.resourceserver.dtos.*;
import com.classmanagement.resourceserver.entities.Classroom;
import com.classmanagement.resourceserver.services.ClassroomService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class ClassroomResource {

    private final ClassroomService classroomService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/classrooms")
    public ResponseEntity<Object> addClassroom(@RequestBody ClassroomDto classroomDto) {
        Classroom newClassroom = classroomService.addNewClassroom(classroomDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(newClassroom.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/classrooms")
    public void updateClassroom(@RequestBody ClassroomDto classroomDto) {
        classroomService.updateClassroom(classroomDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'GROUPLEADER')")
    @GetMapping("/classrooms/building/{id}")
    public List<ClassroomDto> getClassroomsByBuilding(@PathVariable int id) {
        return classroomService.getClassroomsInBuilding(id);
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @PostMapping("/classrooms/{id}/availabilities")
    public void createClassroomAvailability(@RequestBody NewClassroomAvailabilityDto newClassroomAvailabilityDto) {
        classroomService.createClassroomAvailabilities(newClassroomAvailabilityDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'GROUPLEADER')")
    @GetMapping("/classrooms/{id}/availabilities/isDateRangeValid")
    public boolean isDateRangeValid(@PathVariable(name = "id") int classroomId,
            @RequestParam(name = "fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(name = "toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        return classroomService.isDateRangeValid(classroomId, fromDate, toDate);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'GROUPLEADER')")
    @GetMapping("/classrooms/{id}/supervisors")
    public List<UserDto> getClassroomSupervisors(@PathVariable int id) {
        return classroomService.classroomSupervisors(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'GROUPLEADER')")
    @GetMapping("/classrooms/{id}/availabilities")
    public List<ClassroomAvailabilityDto> getClassroomAllAvailabilities(@PathVariable int id) {
        return classroomService.getClassroomAllCurrentAvailability(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'GROUPLEADER')")
    @GetMapping("/classrooms/{id}/availabilities/timeRanges")
    public List<TimeRangeDto> getClassroomAvailabilities(
            @PathVariable int id,
            @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return classroomService.getClassroomAvailability(id, date);
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @PutMapping("/classrooms/{id}/availabilities/shrink")
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
