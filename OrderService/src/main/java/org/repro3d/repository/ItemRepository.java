package org.repro3d.repository;

import org.repro3d.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for {@link Item} entities.
 * This interface extends JpaRepository, providing CRUD operations and additional methods to interact with the Item data.
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    /**
     * Finds a list of items by their name.
     *
     * @param name The name of the items to search for.
     * @return A list of {@link Item} objects that match the specified name. If no items are found, returns an empty list.
     */
    List<Item> findByName(String name);


}
