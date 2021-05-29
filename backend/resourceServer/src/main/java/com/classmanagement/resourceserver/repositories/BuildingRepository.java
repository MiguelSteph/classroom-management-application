package com.classmanagement.resourceserver.repositories;

import com.classmanagement.resourceserver.entities.Building;
import com.classmanagement.resourceserver.entities.Site;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Integer> {
    Building findByCode(String code);
    Building findByCodeAndSite(String name, Site site);
    Building findByNameAndSite(String name, Site site);
}
