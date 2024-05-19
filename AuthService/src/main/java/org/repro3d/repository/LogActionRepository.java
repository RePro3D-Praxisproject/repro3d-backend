package org.repro3d.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.repro3d.model.LogAction;

/**
 * Repository interface for accessing and managing LogAction entities in the database.
 * Provides basic CRUD operations through JpaRepository.
 */
@Repository
public interface LogActionRepository extends JpaRepository<LogAction, Long> {
}
