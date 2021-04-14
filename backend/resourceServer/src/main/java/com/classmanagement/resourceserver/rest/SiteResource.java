package com.classmanagement.resourceserver.rest;

import com.classmanagement.resourceserver.dtos.SiteDto;
import com.classmanagement.resourceserver.services.SiteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class SiteResource {

    private final SiteService siteService;

    @GetMapping("/sites")
    public List<SiteDto> getSites() {
        return siteService.getAll();
    }
}
