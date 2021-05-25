package com.classmanagement.resourceserver.repositories;

import com.classmanagement.resourceserver.entities.Site;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SiteRepository extends JpaRepository<Site, Integer> {
    Site findByName(String name);
    Site findByCode(String code);
    List<Site> findAllByIsEnabled(boolean enabled);
}
