package com.classmanagement.authorizationserver.repositories;

import com.classmanagement.authorizationserver.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
