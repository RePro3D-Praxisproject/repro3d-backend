package org.repro3d.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.repro3d.model.User;

import java.util.Optional;

/**
 * UserRepository is a repository interface for accessing and managing User entities in the database.
 * It provides basic CRUD operations through JpaRepository and a method to find a user by their email.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a User by their email.
     *
     * @param email The email of the user to find.
     * @return An Optional containing the User entity if found, or empty if not.
     */
    Optional<User> findByEmail(String email);
}
