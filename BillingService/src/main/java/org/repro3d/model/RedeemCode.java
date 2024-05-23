package org.repro3d.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RedeemCode represents a redeemable code in the system.
 * It includes the code itself and a flag indicating whether the code has been used.
 */
@Entity
@Table(name = "redeem_codes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedeemCode {

    /**
     * The unique identifier for the redeem code.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rc_id;

    /**
     * The redeem code string.
     * This field has a maximum length of 100 characters.
     */
    @Column(name = "rc_code", length = 100)
    private String rcCode;

    /**
     * A flag indicating whether the redeem code has been used.
     */
    @Column(name = "used")
    private Boolean used;

    /**
     * Constructs a new RedeemCode with the specified code and usage flag.
     *
     * @param encodedCode The redeem code string.
     * @param b           The flag indicating whether the code has been used.
     */
    public RedeemCode(String encodedCode, boolean b) {
        this.rcCode = encodedCode;
        this.used = b;

    }
}
