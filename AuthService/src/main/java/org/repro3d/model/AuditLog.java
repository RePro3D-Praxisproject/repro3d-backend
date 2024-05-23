package org.repro3d.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AuditLog represents an audit log entry in the system.
 * It records actions performed by users, including the user who performed the action,
 * the action performed, and the timestamp of when the action occurred.
 */
@Entity
@Table(name = "audit_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    /**
     * The unique identifier for the audit log entry.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditId;

    /**
     * The user who performed the action.
     * This is a many-to-one relationship with the User entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The action performed by the user.
     * This is a many-to-one relationship with the LogAction entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_id")
    private LogAction action;

    /**
     * The timestamp of when the action was performed.
     * This field is annotated with @Temporal to specify that it is a TIMESTAMP column.
     */
    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
}
