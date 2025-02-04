package org.repro3d.repository;

import org.repro3d.model.Printer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Printer} entities.
 * <p>
 * Acts as a mechanism for encapsulating storage, retrieval, and search behavior which emulates a collection of objects.
 * Through extending {@link JpaRepository}, it inherits standard CRUD operations and the ability to define more complex queries.
 */
@Repository
public interface PrinterRepository extends JpaRepository<Printer, Long> {
}
