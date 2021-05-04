package com.classmanagement.resourceserver.util.mappers;

import com.classmanagement.resourceserver.dtos.*;
import com.classmanagement.resourceserver.entities.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Collectors;

public class DtoMapperUtil {

    public static BookingRequestDto convertToBookingRequestDto(BookingRequest bookingRequest) {
        BookingRequestDto bookingRequestDto = new BookingRequestDto();
        bookingRequestDto.setId(bookingRequest.getId());
        bookingRequestDto.setStatus(bookingRequest.getStatus());
        bookingRequestDto.setCreatedDate(bookingRequest.getCreatedDate());
        bookingRequestDto.setLastUpdatedDate(bookingRequest.getLastUpdatedDate());
        bookingRequestDto.setPurpose(bookingRequest.getPurpose());
        bookingRequestDto.setRejectionReason(bookingRequest.getRejectionReason());
        bookingRequestDto.setBookingDate(bookingRequest.getBookingDate());
        bookingRequestDto.setFromTime(bookingRequest.getFromTime());
        bookingRequestDto.setToTime(bookingRequest.getToTime());

        Building building = bookingRequest.getClassroom().getBuilding();
        Site site = building.getSite();

        BuildingDto buildingDto = new BuildingDto();
        buildingDto.setId(building.getId());
        buildingDto.setCode(building.getCode());
        buildingDto.setName(building.getName());

        SiteDto siteDto = new SiteDto();
        siteDto.setId(site.getId());
        siteDto.setCode(site.getCode());
        siteDto.setName(site.getName());

        ClassroomDto classroom = convertToClassroomDto(bookingRequest.getClassroom());

        bookingRequestDto.setBuilding(buildingDto);
        bookingRequestDto.setClassroom(classroom);
        bookingRequestDto.setSite(siteDto);
        bookingRequestDto.setAssignedTo(convertToUserDto(bookingRequest.getAssignedTo()));
        bookingRequestDto.setCreatedBy(convertToUserDto(bookingRequest.getCreatedBy()));
        return bookingRequestDto;
    }

    public static TimeRangeDto convertToTimeRangeDto(AvailableTimeInterval interval) {
        TimeRangeDto timeRangeDto = new TimeRangeDto();
        timeRangeDto.setFromTime(interval.getFromTime());
        timeRangeDto.setToTime(interval.getToTime());
        return timeRangeDto;
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
