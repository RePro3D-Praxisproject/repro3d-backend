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

/**
 * Receipt represents a payment receipt in the system.
 * It includes details about the payment, the total amount, and the associated order.
 */
@Entity
@Table(name = "receipt")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {

    /**
     * The unique identifier for the receipt.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receipt_id")
    private Long receiptId;

    /**
     * The date and time when the payment was made.
     * This field has a maximum length of 45 characters.
     */
    @Column(name = "paid_on", length = 45)
    private String paidOn;

    /**
     * The total amount paid in the receipt.
     */
    @Column(name = "sum_total")
    private Long sumTotal;

    /**
     * The order associated with the receipt.
     * This is a many-to-one relationship with the Order entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;
}
