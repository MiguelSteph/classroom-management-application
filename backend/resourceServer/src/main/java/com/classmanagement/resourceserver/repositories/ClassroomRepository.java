package com.classmanagement.resourceserver.repositories;

import com.classmanagement.resourceserver.entities.Building;
import com.classmanagement.resourceserver.entities.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {
    Classroom findByCode(String code);
    Classroom findByCodeAndBuilding(String name, Building site);
    Classroom findByNameAndBuilding(String name, Building site);
}
