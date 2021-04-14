package com.classmanagement.resourceserver.repositories;

import com.classmanagement.resourceserver.entities.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityRepository extends JpaRepository<Availability, Integer> {
}
