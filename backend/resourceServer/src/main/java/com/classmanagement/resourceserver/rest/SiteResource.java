package com.classmanagement.resourceserver.rest;

import com.classmanagement.resourceserver.dtos.SiteDto;
import com.classmanagement.resourceserver.entities.Site;
import com.classmanagement.resourceserver.services.SiteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class SiteResource {

    private final SiteService siteService;

    @PostMapping("/sites")
    public ResponseEntity<Object> addSite(@RequestBody SiteDto siteDto) {
        Site newSite = siteService.addNewSite(siteDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(newSite.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/sites")
    public void updateSite(@RequestBody SiteDto siteDto) {
        siteService.updateSite(siteDto);
    }

    @GetMapping("/sites")
    public List<SiteDto> getSites() {
        return siteService.getAll();
    }

    @GetMapping("/sites/enabled")
    public List<SiteDto> getEnabledSites() {
        return siteService.getEnabledSites();
    }
}
