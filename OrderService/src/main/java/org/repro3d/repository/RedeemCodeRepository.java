package org.repro3d.repository;

import org.repro3d.model.RedeemCode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository interface for {@link RedeemCode} entities.
 * This interface extends JpaRepository, providing CRUD operations and additional query capabilities for RedeemCode entities.
 * It supports the management of redeem codes, including their creation, retrieval, update, and deletion,
 * along with custom queries such as finding a redeem code by its code value.
 * Duplicated from BillingService.
 */
public interface RedeemCodeRepository extends JpaRepository<RedeemCode, Long> {
    Optional<RedeemCode> findByRcCode(String rcCode);
}
