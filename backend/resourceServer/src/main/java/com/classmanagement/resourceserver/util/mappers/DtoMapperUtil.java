package com.classmanagement.resourceserver.util.mappers;

import com.classmanagement.resourceserver.dtos.*;
import com.classmanagement.resourceserver.entities.*;

import java.util.stream.Collectors;

public class DtoMapperUtil {

    public static TimeIntervalDto convertToTimeIntervalDto(AvailableTimeInterval interval) {
        TimeIntervalDto timeIntervalDto = new TimeIntervalDto();
        timeIntervalDto.setId(interval.getId());
        timeIntervalDto.setFromDate(interval.getFromDate());
        timeIntervalDto.setToDate(interval.getToDate());
        timeIntervalDto.setFromTime(interval.getFromTime());
        timeIntervalDto.setToTime(interval.getToTime());
        timeIntervalDto.setWeekDay(interval.getWeekDay());
        timeIntervalDto.setCreatedDate(interval.getCreatedDate());
        return timeIntervalDto;
    }

    public static UserDto convertToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole().getName());
        return userDto;
    }

    public static SiteDto convertToSiteDto(Site site) {
        SiteDto siteDto = new SiteDto();
        siteDto.setId(site.getId());
        siteDto.setCode(site.getCode());
        siteDto.setName(site.getName());
        siteDto.setBuildings(
                site.getBuildings()
                        .stream()
                        .map(DtoMapperUtil::convertToBuildingDto)
                        .collect(Collectors.toList())
        );
        return siteDto;
    }

    public static BuildingDto convertToBuildingDto(Building building) {
        BuildingDto buildingDto = new BuildingDto();
        buildingDto.setId(building.getId());
        buildingDto.setCode(building.getCode());
        buildingDto.setName(building.getName());
        buildingDto.setClassrooms(
                building.getClassrooms()
                        .stream()
                        .map(DtoMapperUtil::convertToClassroomDto)
                        .collect(Collectors.toList())
        );
        return buildingDto;
    }

    public static ClassroomDto convertToClassroomDto(Classroom classroom) {
        ClassroomDto classroomDto = new ClassroomDto();
        classroomDto.setId(classroom.getId());
        classroomDto.setName(classroom.getName());
        classroomDto.setCode(classroom.getCode());
        return classroomDto;
    }
}
