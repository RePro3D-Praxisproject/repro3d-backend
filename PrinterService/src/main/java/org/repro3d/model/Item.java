package org.repro3d.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an item entity within the OrderDB schema.
 * It is duplicated in otder to be used within PrinterService module.
 */
@Entity
@Table(name = "item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    /**
     * The unique identifier for the item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long item_id;

    /**
     * The name of the item.
     */
    private String name;

    /**
     * A brief description of the item.
     */
    private String description;

    /**
     * The estimated time (in minutes) it takes to produce the item.
     */
    @Column
    private Integer est_time;

    /**
     * The dimensions of the item, including length, width, and height.
     */
    private String dimensions;

    /**
     * A reference to a g-code file associated with the item.
     */
    private String file_ref;

    /**
     * The material from which the item is intended to be made.
     */
    private String material;

    /**
     * The cost associated with the item.
     */
    private String cost;
    /**
     * Image url associated with the item.
     */
    @Column
    private String image_url;
}
