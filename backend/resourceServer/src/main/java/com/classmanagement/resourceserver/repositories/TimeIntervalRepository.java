package com.classmanagement.resourceserver.repositories;

import com.classmanagement.resourceserver.entities.TimeInterval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeIntervalRepository extends JpaRepository<TimeInterval, Long> {
}
