package repro3d.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the association between an order and its items, detailing the items
 * that are part of each order. It also includes references to external job IDs
 * associated with each item in the context of the order.
 */
@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItems {

    /**
     * Unique identifier for the order-item association.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oi_id;

    /**
     * The item associated with this order. This relationship is managed with a
     * many-to-one association, as each order can contain multiple items.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    /**
     * An external job ID from a printer database (PrinterDb) that is associated
     * with processing this item within the order. This serves as a link to
     * external systems managing the physical or logistical processing of items.
     */
    @Column
    private Long job_id;

    /**
     * The order associated with this item. This is part of a many-to-one
     * relationship, indicating that multiple items (and thus multiple
     * order-item associations) can belong to a single order.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}
