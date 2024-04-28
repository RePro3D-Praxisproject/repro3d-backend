package org.repro3d.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.repro3d.model.Role;

@Entity
@Table(name = "user_entity", schema = "AuthDb")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "billing_addr", length = 100)
    private String billingAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Role role;
}