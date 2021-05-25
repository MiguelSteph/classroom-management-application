package com.classmanagement.resourceserver.services;

import com.classmanagement.resourceserver.dtos.SiteDto;
import com.classmanagement.resourceserver.entities.Site;

import java.util.List;

public interface SiteService {
    List<SiteDto> getAll();

    List<SiteDto> getEnabledSites();

    Site addNewSite(SiteDto siteDto);

    Site updateSite(SiteDto siteDto);
}
