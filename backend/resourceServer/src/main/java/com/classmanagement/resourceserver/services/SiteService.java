package com.classmanagement.resourceserver.services;

import com.classmanagement.resourceserver.dtos.SiteDto;

import java.util.List;

public interface SiteService {
    List<SiteDto> getAll();
}
