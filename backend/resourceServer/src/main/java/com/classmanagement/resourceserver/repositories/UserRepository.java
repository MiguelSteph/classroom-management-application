package com.classmanagement.resourceserver.repositories;

import com.classmanagement.resourceserver.entities.Role;
import com.classmanagement.resourceserver.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    List<User> findAllByRole(Role role);

}
