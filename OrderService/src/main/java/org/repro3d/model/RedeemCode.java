package org.repro3d.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "redeem_codes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedeemCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rc_id;

    @Column(name = "rc_code", length = 100)
    private String rcCode;

    @Column(name = "used")
    private Boolean used;
}
