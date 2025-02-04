package org.repro3d.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * Represents an order entity for the application, detailing each order's information,
 * including its creation date, associated user, and redeem code.
 * Duplicated from the OrderService module.
 */
@Entity
@Table(name = "order_entity")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    /**
     * Unique identifier for the order.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    /**
     * The date and time when the order was placed.
     */
    @Column(name = "order_date")
    private Date orderDate;

    /**
     * The ID of the user who placed the order. This is an external reference
     * to a user ID managed by an authentication database (AuthDb).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    /**
     * The ID of the redeem code associated with the order. This is an external
     * reference to a redeem code ID managed by a billing database (BillingDb).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "rc_id", referencedColumnName = "rc_id")
    private RedeemCode redeemCode;
}
