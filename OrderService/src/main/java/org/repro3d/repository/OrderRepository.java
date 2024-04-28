    package org.repro3d.repository;

    import org.repro3d.model.Order;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    /**
     * Repository interface for {@link Order} entities.
     * This interface extends JpaRepository, providing CRUD operations and additional methods to interact with the Order data.
     */
    @Repository
    public interface OrderRepository extends JpaRepository<Order, Long> {
    }
