package com.classmanagement.resourceserver.util.mappers;

import com.classmanagement.resourceserver.dtos.BuildingDto;
import com.classmanagement.resourceserver.dtos.ClassroomDto;
import com.classmanagement.resourceserver.dtos.SiteDto;
import com.classmanagement.resourceserver.dtos.UserDto;
import com.classmanagement.resourceserver.entities.Building;
import com.classmanagement.resourceserver.entities.Classroom;
import com.classmanagement.resourceserver.entities.Site;
import com.classmanagement.resourceserver.entities.User;

import java.util.stream.Collectors;

public class DtoMapperUtil {

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
