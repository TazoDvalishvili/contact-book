package com.project.contactbook.repository;

import com.project.contactbook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User repository responsible for managing db operations for user entity.
 *
 * @author Tazo Dvalishvili
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Boolean existsByUsername(String username);
}
