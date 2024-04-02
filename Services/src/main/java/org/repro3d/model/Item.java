package org.repro3d.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long item_id;

    @Column(nullable = false, length = 128)
    private String name;

    @Column(length = 256)
    private String description;

    @Column
    private Long creator_user_id;

    @Column(length = 64)
    private String material;

    @Column(nullable = false)
    private Double base_price;

    @Column(nullable = false, length = 512)
    private String path;
}
