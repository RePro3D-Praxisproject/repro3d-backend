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
 * LogAction represents an action that can be logged in the audit log.
 * It includes an identifier and a description of the action.
 */
@Entity
@Table(name = "log_action")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogAction {

    /**
     * The unique identifier for the log action entry.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long laId;

    /**
     * The description of the action.
     * This field has a maximum length of 45 characters.
     */
    @Column(name = "action", length = 45)
    private String action;
}
