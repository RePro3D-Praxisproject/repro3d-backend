package org.repro3d.repository;

import org.repro3d.model.Receipt;
import org.repro3d.model.RedeemCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Receipt} entities.
 * This interface extends JpaRepository, providing CRUD operations and additional methods
 * to interact with the Receipt data. It enables the management of receipt entries,
 * facilitating create, read, update, and delete operations.
 */
@Repository
public interface ReceiptRepository extends JpaRepository <Receipt, Long> {
}