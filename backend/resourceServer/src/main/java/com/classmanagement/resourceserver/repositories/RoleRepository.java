package com.classmanagement.resourceserver.repositories;

import com.classmanagement.resourceserver.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
