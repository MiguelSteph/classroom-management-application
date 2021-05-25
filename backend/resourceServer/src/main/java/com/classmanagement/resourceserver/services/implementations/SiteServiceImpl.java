package com.classmanagement.resourceserver.services.implementations;

import com.classmanagement.resourceserver.dtos.SiteDto;
import com.classmanagement.resourceserver.entities.Site;
import com.classmanagement.resourceserver.exceptions.UserCausedBackendException;
import com.classmanagement.resourceserver.repositories.SiteRepository;
import com.classmanagement.resourceserver.services.SiteService;
import com.classmanagement.resourceserver.util.mappers.DtoMapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SiteServiceImpl implements SiteService {

    private static final int SITE_CODE_MAX_SIZE = 20;

    private final SiteRepository siteRepository;

    @Override
    public Site updateSite(SiteDto siteDto) {
        this.checkForUpdateSite(siteDto);

        Site site = siteRepository.getOne(siteDto.getId());
        site.setCode(siteDto.getCode());
        site.setName(siteDto.getName());
        site.setEnabled(siteDto.isEnabled());

        return siteRepository.save(site);
    }

    private void checkForUpdateSite(SiteDto siteDto) {
        if (siteDto.getId() == null) {
            throw new UserCausedBackendException("Unexpected error. Please try again.");
        }

        if (siteDto.getCode() == null || siteDto.getCode().isEmpty()) {
            throw new UserCausedBackendException("Site Code cannot be empty.");
        }

        if (siteDto.getCode().length() > SITE_CODE_MAX_SIZE) {
            throw new UserCausedBackendException("Site Code is too long. Site Code should not be more than 20 characters.");
        }

        if (siteDto.getName() == null || siteDto.getName().isEmpty()) {
            throw new UserCausedBackendException("Site Name cannot be empty");
        }

        Site siteByName = siteRepository.findByName(siteDto.getName());
        Site siteByCode = siteRepository.findByCode(siteDto.getCode());

        if (siteByName != null && !siteByName.getId().equals(siteDto.getId())) {
            throw new UserCausedBackendException("Site Name already exists.");
        }

        if (siteByCode != null && !siteByCode.getId().equals(siteDto.getId())) {
            throw new UserCausedBackendException("Site Code already exists.");
        }
    }

    @Override
    public Site addNewSite(SiteDto siteDto) {
        this.checkForAddNewSite(siteDto);

        Site site = new Site();
        site.setCode(siteDto.getCode());
        site.setName(siteDto.getName());
        site.setEnabled(true);

        return siteRepository.save(site);
    }

    private void checkForAddNewSite(SiteDto siteDto) {
        if (siteDto.getCode() == null || siteDto.getCode().isEmpty()) {
            throw new UserCausedBackendException("Site Code is not provided");
        }

        if (siteDto.getCode().length() > SITE_CODE_MAX_SIZE) {
            throw new UserCausedBackendException("Site Code is too long. Site Code should not be more than 20 characters.");
        }

        if (siteDto.getName() == null || siteDto.getName().isEmpty()) {
            throw new UserCausedBackendException("Site Name is not provided");
        }

        Site siteByName = siteRepository.findByName(siteDto.getName());
        Site siteByCode = siteRepository.findByCode(siteDto.getCode());

        if (siteByName != null) {
            throw new UserCausedBackendException("Site Name already exists.");
        }

        if (siteByCode != null) {
            throw new UserCausedBackendException("Site Code already exists.");
        }
    }

    @Override
    public List<SiteDto> getEnabledSites() {
        List<Site> siteList = siteRepository.findAllByIsEnabled(true);
        if (siteList.isEmpty()) return Collections.emptyList();
        return siteList.stream()
                .map(site -> DtoMapperUtil.convertToSiteDto(site, true))
                .sorted(Comparator.comparing(SiteDto::getName))
                .collect(Collectors.toList());
    }

    @Override
    public List<SiteDto> getAll() {
        List<Site> siteList = siteRepository.findAll();
        if (siteList.isEmpty()) return Collections.emptyList();
        return siteList.stream()
                .map(DtoMapperUtil::convertToSiteDto)
                .sorted(Comparator.comparing(SiteDto::getName))
                .collect(Collectors.toList());
    }
}
