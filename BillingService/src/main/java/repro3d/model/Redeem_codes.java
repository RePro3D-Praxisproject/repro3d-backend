package repro3d.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name="redeem_code", schema = "BillingDB")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Redeem_codes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rc_id;


    @Column(length = 45)
    private String rc_code;

    @Column
    private Boolean used;




}
