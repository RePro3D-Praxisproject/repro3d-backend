package org.repro3d.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an item entity in the system.
 * This class is a JPA entity that maps to the 'item' table in the database.
 * It includes details about the item such as name, description, creator's user ID, material, base price, and path.
 */
@Entity
@Table(name = "item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    /**
     * The unique identifier for the item. This ID is generated automatically.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long item_id;

    /**
     * The name of the item. This field is not nullable and has a maximum length of 128 characters.
     */
    @Column(nullable = false, length = 128)
    private String name;

    /**
     * The description of the item. This field is optional and has a maximum length of 256 characters.
     */
    @Column(length = 256)
    private String description;

    /**
     * The user ID of the item's creator. This represents a foreign key linking to the user entity.
     */
    @Column
    private Long creator_user_id;

    /**
     * The material of the item. This field is optional and has a maximum length of 64 characters.
     */
    @Column(length = 64)
    private String material;

    /**
     * The base price of the item. This field is not nullable.
     */
    @Column(nullable = false)
    private Double base_price;

    /**
     * The path to the item's image or resource. This field is not nullable and has a maximum length of 512 characters.
     */
    @Column(nullable = false, length = 512)
    private String path;
}
