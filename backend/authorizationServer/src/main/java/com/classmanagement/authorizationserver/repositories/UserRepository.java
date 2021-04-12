package com.classmanagement.authorizationserver.repositories;

import com.classmanagement.authorizationserver.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
