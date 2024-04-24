package repro3d.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "receipt", schema ="BillingDB" )
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receipt_id;

    @Column(length = 45)
    private String paid_on;

    @Column
    private Integer sum_total;

    @Column
    private Integer order_id;



}
