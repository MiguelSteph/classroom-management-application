package com.classmanagement.resourceserver.services.implementations;

import com.classmanagement.resourceserver.dtos.BuildingDto;
import com.classmanagement.resourceserver.dtos.ClassroomDto;
import com.classmanagement.resourceserver.dtos.SiteDto;
import com.classmanagement.resourceserver.entities.Building;
import com.classmanagement.resourceserver.entities.Classroom;
import com.classmanagement.resourceserver.entities.Site;
import com.classmanagement.resourceserver.repositories.SiteRepository;
import com.classmanagement.resourceserver.services.SiteService;
import com.classmanagement.resourceserver.util.mappers.DtoMapperUtil;
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
                .map(DtoMapperUtil::convertToSiteDto)
                .collect(Collectors.toList());
    }
}
