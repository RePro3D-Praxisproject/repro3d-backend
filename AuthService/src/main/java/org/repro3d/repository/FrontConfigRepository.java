package org.repro3d.repository;

import org.repro3d.model.FrontConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing FrontConfig entities in the database.
 * Provides basic CRUD operations through JpaRepository and a method to find a configuration by its key.
 */
@Repository
public interface FrontConfigRepository extends JpaRepository<FrontConfig, Long> {

    /**
     * Finds a FrontConfig by its key.
     *
     * @param key The key of the configuration to find.
     * @return The FrontConfig entity with the specified key.
     */
    FrontConfig findByKey(String key);
}