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
 * FrontConfig represents a configuration setting for the front-end.
 * It includes a unique key and its corresponding value.
 */
@Entity
@Table(name = "front_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FrontConfig {

    /**
     * The unique identifier for the configuration entry.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The key of the configuration setting.
     * This field is unique and cannot be null.
     */
    @Column(name = "config_key", unique = true, nullable = false)
    private String key;

    /**
     * The value of the configuration setting.
     * This field cannot be null.
     */
    @Column(name = "config_value", nullable = false)
    private String value;

    /**
     * Constructs a new FrontConfig with the specified key and value.
     *
     * @param key   The key of the configuration setting.
     * @param value The value of the configuration setting.
     */
    public FrontConfig(String key, String value) {
        this.key = key;
        this.value = value;
    }
}