package org.repro3d.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a role entity within the system, associated with the AuthDb schema.
 * Roles define the access and permissions levels within the application.
 */
@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    /**
     * The unique identifier for the role.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    /**
     * The name of the role, such as 'Admin', 'User', etc.
     */
    @Column(name = "role_name", length = 45)
    private String roleName;

}
