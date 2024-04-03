package org.repro3d.controller;

import org.repro3d.model.Item;
import org.repro3d.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing items.
 * Provides endpoints for creating, retrieving, updating, and deleting items.
 */
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    /**
     * Constructs an ItemController with the given item service.
     * @param itemService The service used for item operations.
     */
    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Creates a new item.
     *
     * @param item The item to create.
     * @return ResponseEntity containing the result of the creation operation.
     */
    @PostMapping
    public ResponseEntity<?> createItem(@RequestBody Item item) {
        return itemService.createItem(item);
    }

    /**
     * Retrieves an item by its ID.
     *
     * @param id The ID of the item to retrieve.
     * @return ResponseEntity containing the requested item or an error message if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    /**
     * Retrieves all items.
     *
     * @return ResponseEntity containing all items.
     */
    @GetMapping
    public ResponseEntity<?> getAllItems() {
        return itemService.getAllItems();
    }

    /**
     * Updates an existing item identified by its ID.
     *
     * @param id The ID of the item to update.
     * @param itemDetails The new details of the item.
     * @return ResponseEntity containing the result of the update operation.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody Item itemDetails) {
        return itemService.updateItem(id, itemDetails);
    }

    /**
     * Deletes an item by its ID.
     *
     * @param id The ID of the item to delete.
     * @return ResponseEntity containing the result of the delete operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        return itemService.deleteItem(id);
    }
}
