package com.classmanagement.resourceserver.repositories;

import com.classmanagement.resourceserver.entities.Site;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteRepository extends JpaRepository<Site, Integer> {
    Site findByName(String name);
    Site findByCode(String code);
}
