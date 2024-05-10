package org.repro3d.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a printer entity within the system.
 * <p>
 * This entity stores detailed information about a printer, including its name,
 * location, IP address, and an API key for authentication or integration purposes.
 */
@Entity
@Table(name = "printer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Printer {

    /**
     * The unique identifier for the printer.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long printer_id;

    /**
     * The name of the printer. This can be a model name or a custom name given to the printer.
     */
    @Column(length = 45)
    private String name;

    /**
     * The location of the printer.
     */
    @Column(length = 45)
    private String location;

    /**
     * The IP address of the printer. Used for network communications and identification.
     */
    @Column(length = 50)
    private String ip_addr;

    /**
     * An OctoPrint API key associated with the printer.
     */
    @Column(length = 32)
    private String apikey;
}
