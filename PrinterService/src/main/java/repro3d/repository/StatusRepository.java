package repro3d.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import repro3d.model.Status;

/**
 * Repository interface for {@link Status} entities.
 * <p>
 * Provides an abstraction layer to manage the set of status entities. By extending
 * {@link JpaRepository}, it automatically inherits a suite of CRUD operations and
 * the capability to define custom queries for interacting with the database.
 */
@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
}
