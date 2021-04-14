package com.classmanagement.resourceserver.repositories;

import com.classmanagement.resourceserver.entities.ClassroomSupervisor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomSupervisorRepository extends JpaRepository<ClassroomSupervisor, Integer> {
}
