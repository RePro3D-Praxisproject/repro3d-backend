package org.repro3d.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "receipt", schema = "BillingDb")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer receiptId;

    @Column(name = "paid_on", length = 45)
    private String paidOn;

    @Column(name = "sum_total")
    private Integer sumTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;
}
