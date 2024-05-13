package org.repro3d.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * A custom RequestBody for Order and Item.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrder {
    Order order;
    Item[] items;
}
