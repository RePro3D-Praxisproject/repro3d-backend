package org.repro3d.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.repro3d.model.User;

import java.util.Optional;

/**
 * Repository interface for accessing and managing User entities in the database.
 * Provides basic CRUD operations through JpaRepository and a method to find a user by their email.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
