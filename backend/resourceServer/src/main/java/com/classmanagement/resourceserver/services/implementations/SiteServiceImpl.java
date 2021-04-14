package com.classmanagement.resourceserver.services.implementations;

import com.classmanagement.resourceserver.dtos.BuildingDto;
import com.classmanagement.resourceserver.dtos.ClassroomDto;
import com.classmanagement.resourceserver.dtos.SiteDto;
import com.classmanagement.resourceserver.entities.Building;
import com.classmanagement.resourceserver.entities.Classroom;
import com.classmanagement.resourceserver.entities.Site;
import com.classmanagement.resourceserver.repositories.SiteRepository;
import com.classmanagement.resourceserver.services.SiteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SiteServiceImpl implements SiteService {

    private final SiteRepository siteRepository;

    @Override
    public List<SiteDto> getAll() {
        List<Site> siteList = siteRepository.findAll();
        if (siteList.isEmpty()) return Collections.emptyList();
        return siteList.stream()
                .map(this::convertToSiteDto)
                .collect(Collectors.toList());
    }

    private SiteDto convertToSiteDto(Site site) {
        SiteDto siteDto = new SiteDto();
        siteDto.setId(site.getId());
        siteDto.setCode(site.getCode());
        siteDto.setName(site.getName());
        siteDto.setBuildings(
                site.getBuildings()
                        .stream()
                        .map(this::convertToBuildingDto)
                        .collect(Collectors.toList())
        );
        return siteDto;
    }

    private BuildingDto convertToBuildingDto(Building building) {
        BuildingDto buildingDto = new BuildingDto();
        buildingDto.setId(building.getId());
        buildingDto.setCode(building.getCode());
        buildingDto.setName(building.getName());
        buildingDto.setClassrooms(
                building.getClassrooms()
                        .stream()
                        .map(this::convertToClassroomDto)
                        .collect(Collectors.toList())
        );
        return buildingDto;
    }

    private ClassroomDto convertToClassroomDto(Classroom classroom) {
        ClassroomDto classroomDto = new ClassroomDto();
        classroomDto.setId(classroom.getId());
        classroomDto.setName(classroom.getName());
        classroomDto.setCode(classroom.getCode());
        return classroomDto;
    }
}
