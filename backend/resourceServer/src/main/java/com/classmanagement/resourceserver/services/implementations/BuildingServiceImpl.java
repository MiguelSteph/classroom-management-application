package com.classmanagement.resourceserver.services.implementations;

import com.classmanagement.resourceserver.dtos.BuildingDto;
import com.classmanagement.resourceserver.dtos.SiteDto;
import com.classmanagement.resourceserver.entities.Building;
import com.classmanagement.resourceserver.entities.Site;
import com.classmanagement.resourceserver.exceptions.UserCausedBackendException;
import com.classmanagement.resourceserver.repositories.BuildingRepository;
import com.classmanagement.resourceserver.repositories.SiteRepository;
import com.classmanagement.resourceserver.services.BuildingService;
import com.classmanagement.resourceserver.util.mappers.DtoMapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BuildingServiceImpl implements BuildingService {
    private static final int BUILDING_CODE_MAX_SIZE = 20;

    private final BuildingRepository buildingRepository;
    private final SiteRepository siteRepository;

    @Override
    public List<BuildingDto> getAll() {
        List<Building> buildingDtos = buildingRepository.findAll();
        return buildingDtos.stream()
                .map(DtoMapperUtil::convertToBuildingDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BuildingDto> getBuildingsInSite(int siteId) {
        List<Building> buildingDtos = siteRepository.getOne(siteId).getBuildings();
        return buildingDtos.stream()
                .map(DtoMapperUtil::convertToBuildingDto)
                .collect(Collectors.toList());
    }

    @Override
    public Building addNewBuilding(BuildingDto buildingDto) {
        Site site = getBuildingSite(buildingDto);
        checkForAddNewBuilding(buildingDto, site);

        Building building = new Building();
        building.setCode(buildingDto.getCode());
        building.setName(buildingDto.getName());
        building.setSite(site);
        building.setEnabled(true);

        return buildingRepository.save(building);
    }

    private Site getBuildingSite(BuildingDto buildingDto) {
        if (buildingDto.getSiteId() == null) {
            throw new UserCausedBackendException("Unexpected error. Please try again.");
        }

        return siteRepository.getOne(buildingDto.getSiteId());
    }

    private void checkForAddNewBuilding(BuildingDto buildingDto, Site site) {
        if (buildingDto.getCode() == null || buildingDto.getCode().isEmpty()) {
            throw new UserCausedBackendException("Building Code is not provided");
        }

        if (buildingDto.getCode().length() > BUILDING_CODE_MAX_SIZE) {
            throw new UserCausedBackendException("Building Code is too long. Building Code should not be more than 20 characters.");
        }

        if (buildingDto.getName() == null || buildingDto.getName().isEmpty()) {
            throw new UserCausedBackendException("Building Name is not provided");
        }

        Building buildingByName = buildingRepository.findByNameAndSite(buildingDto.getName(), site);
        Building buildingByCode = buildingRepository.findByCodeAndSite(buildingDto.getCode(), site);

        if (buildingByName != null) {
            throw new UserCausedBackendException("Building Name already exists.");
        }

        if (buildingByCode != null) {
            throw new UserCausedBackendException("Building Code already exists.");
        }
    }

    @Override
    public Building updateBuilding(BuildingDto buildingDto) {
        Site site = getBuildingSite(buildingDto);
        checkForUpdateBuilding(buildingDto, site);

        Building building = buildingRepository.getOne(buildingDto.getId());
        building.setCode(buildingDto.getCode());
        building.setName(buildingDto.getName());
        building.setSite(site);
        building.setEnabled(buildingDto.isEnabled());

        return buildingRepository.save(building);
    }

    private void checkForUpdateBuilding(BuildingDto buildingDto, Site site) {
        if (buildingDto.getId() == null) {
            throw new UserCausedBackendException("Unexpected error. Please try again.");
        }

        if (buildingDto.getCode() == null || buildingDto.getCode().isEmpty()) {
            throw new UserCausedBackendException("Building Code is not provided");
        }

        if (buildingDto.getCode().length() > BUILDING_CODE_MAX_SIZE) {
            throw new UserCausedBackendException("Building Code is too long. Building Code should not be more than 20 characters.");
        }

        if (buildingDto.getName() == null || buildingDto.getName().isEmpty()) {
            throw new UserCausedBackendException("Building Name is not provided");
        }

        Building buildingByName = buildingRepository.findByNameAndSite(buildingDto.getName(), site);
        Building buildingByCode = buildingRepository.findByCodeAndSite(buildingDto.getCode(), site);

        if (buildingByName != null && !buildingByName.getId().equals(buildingDto.getId())) {
            throw new UserCausedBackendException("Building Name already exists.");
        }

        if (buildingByCode != null && !buildingByCode.getId().equals(buildingDto.getId())) {
            throw new UserCausedBackendException("Building Code already exists.");
        }
    }
}
