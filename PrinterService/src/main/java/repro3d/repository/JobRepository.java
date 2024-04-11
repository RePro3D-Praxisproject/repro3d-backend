package repro3d.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import repro3d.model.Job;

/**
 * Repository interface for {@link Job} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing CRUD operations and additional
 * methods to interact with the database for {@link Job} entities.
 */
@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
}
