package org.repro3d.repository;

import org.repro3d.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByName(String name);
    List<Item> findByCreatorUserId(Long creatorUserId);
}
