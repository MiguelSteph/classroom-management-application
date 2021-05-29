package com.classmanagement.resourceserver.services;

import com.classmanagement.resourceserver.dtos.BuildingDto;
import com.classmanagement.resourceserver.entities.Building;

import java.util.List;

public interface BuildingService {
    List<BuildingDto> getAll();

    List<BuildingDto> getBuildingsInSite(int siteId);

    Building addNewBuilding(BuildingDto buildingDto);

    Building updateBuilding(BuildingDto buildingDto);
}
