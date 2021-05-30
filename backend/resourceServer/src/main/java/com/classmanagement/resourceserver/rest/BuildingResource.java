package com.classmanagement.resourceserver.rest;

import com.classmanagement.resourceserver.dtos.BuildingDto;
import com.classmanagement.resourceserver.entities.Building;
import com.classmanagement.resourceserver.services.BuildingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class BuildingResource {

    private final BuildingService buildingService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/buildings")
    public ResponseEntity<Object> addBuilding(@RequestBody BuildingDto buildingDto) {
        Building newBuilding = buildingService.addNewBuilding(buildingDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(newBuilding.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/buildings")
    public void updateBuilding(@RequestBody BuildingDto buildingDto) {
        buildingService.updateBuilding(buildingDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'GROUPLEADER')")
    @GetMapping("/buildings")
    public List<BuildingDto> getAllBuildings() {
        return buildingService.getAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'GROUPLEADER')")
    @GetMapping("/buildings/site/{id}")
    public List<BuildingDto> getBuildingBySite(@PathVariable int id) {
        return buildingService.getBuildingsInSite(id);
    }
}
