package org.repro3d.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a status entity for jobs in the system.
 * <p>
 * This entity is used to define the various states a job can be in, such as 'Pending',
 * 'In Progress', 'Completed', etc. It provides a way to track and manage the lifecycle
 * of a job.
 */
@Entity
@Table(name = "status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Status {

    /**
     * The unique identifier for the status.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long status_id;

    /**
     * The name of the status. This describes the current state of a job and can include
     * values like 'Pending', 'In Progress', 'Completed', and more.
     */
    @Column(length = 45)
    private String status;
}
