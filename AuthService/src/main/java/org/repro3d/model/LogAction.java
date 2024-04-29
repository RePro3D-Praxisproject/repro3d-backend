package org.repro3d.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "log_action", schema = "AuthDb")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long laId;

    @Column(name = "action", length = 45)
    private String action;
}
