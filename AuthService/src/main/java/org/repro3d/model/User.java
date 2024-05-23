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
 * User represents a user entity in the system.
 * It includes the user's email, billing address, role, and password hash.
 */
@Entity
@Table(name = "user_entity")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    /**
     * The email address of the user.
     * This field is unique and has a maximum length of 50 characters.
     */
    @Column(name = "email", length = 50, unique = true)
    private String email;

    /**
     * The billing address of the user.
     * This field has a maximum length of 100 characters.
     */
    @Column(name = "billing_addr", length = 100)
    private String billingAddress;

    /**
     * The role of the user.
     * This is a many-to-one relationship with the Role entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "role", referencedColumnName = "role_id")
    private Role role;

    /**
     * The hashed password of the user.
     * This field has a maximum length of 256 characters.
     */
    @Column(name = "password_hash", length =256)
    private String passwordHash;
}
