    package org.repro3d.repository;

    import org.repro3d.model.Order;
    import org.repro3d.model.RedeemCode;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import java.util.List;

    /**
     * Repository interface for {@link Order} entities.
     * This interface extends JpaRepository, providing CRUD operations and additional methods to interact with the Order data.
     */
    @Repository
    public interface OrderRepository extends JpaRepository<Order, Long> {
        List<Order> findAllByRedeemCode(RedeemCode redeemCode);
    }
