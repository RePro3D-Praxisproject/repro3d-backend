package org.repro3d.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "front_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FrontConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "config_key", unique = true, nullable = false)
    private String key;

    @Column(name = "config_value", nullable = false)
    private String value;

    public FrontConfig(String key, String value) {
        this.key = key;
        this.value = value;
    }
}