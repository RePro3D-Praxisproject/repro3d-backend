package org.repro3d.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    private Item item;

    /**
     * The job associated with this order item. This is linked to a specific job
     * handling the item within the context of this order.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "job_id", referencedColumnName = "job_id")
    private Job job;

    /**
     * The order associated with this item. This is part of a many-to-one
     * relationship, indicating that multiple items (and thus multiple
     * order-item associations) can belong to a single order.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;
}
