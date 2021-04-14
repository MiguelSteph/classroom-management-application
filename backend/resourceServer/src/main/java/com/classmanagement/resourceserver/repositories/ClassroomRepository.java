package com.classmanagement.resourceserver.repositories;

import com.classmanagement.resourceserver.entities.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {
}
