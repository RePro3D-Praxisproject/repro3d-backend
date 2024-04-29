package org.repro3d.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.repro3d.model.Item;

/**
 * Repository interface for {@link Item} entities.
 * <p>
 * This interface extends {@link JpaRepository}, and is used solely to check for existence of an item
 * in applicable methods.
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
