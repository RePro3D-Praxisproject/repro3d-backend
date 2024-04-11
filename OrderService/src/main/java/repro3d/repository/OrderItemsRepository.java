package repro3d.repository;

import repro3d.model.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for  {@link OrderItems} entities.
 * This interface extends JpaRepository, providing CRUD operations and additional methods to interact with the OrderItems data.
 *
 */
@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
}
