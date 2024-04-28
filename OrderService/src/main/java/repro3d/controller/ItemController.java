package repro3d.controller;

import repro3d.model.Item;
import repro3d.service.ItemService;
import repro3d.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing {@link Item} entities.
 * Provides endpoints for CRUD operations on items, including creation, retrieval,
 * updating, and deletion of items, as well as searching for items by name.
 */
@RestController
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;

    /**
     * Constructs an {@code ItemController} with the necessary {@link ItemService}.
     * @param itemService The service used to perform operations on items.
     */
    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Creates a new item.
     * @param item The item to create.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
     *         the result of the operation.
     */
    @PostMapping
    public ResponseEntity<ApiResponse> createItem(@RequestBody Item item) {
        return itemService.createItem(item);
    }

    /**
     * Retrieves an item by its ID.
     * @param id The ID of the item to retrieve.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the item
     *         if found or an error message otherwise.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    /**
     * Retrieves all items.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with a list
     *         of all items.
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getAllItems() {
        return itemService.getAllItems();
    }

    /**
     * Finds items by their name.
     * @param name The name to search for.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with a list
     *         of items that match the specified name. If no items are found, returns an empty list.
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse> getItemsByName(@RequestParam String name) {
        return itemService.findByName(name);
    }

    /**
     * Updates the details of an existing item.
     * @param id The ID of the item to update.
     * @param itemDetails The new details for the item.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
     *         the result of the update operation.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateItem(@PathVariable Long id, @RequestBody Item itemDetails) {
        return itemService.updateItem(id, itemDetails);
    }

    /**
     * Deletes an item by its ID.
     * @param id The ID of the item to delete.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
     *         the result of the delete operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteItem(@PathVariable Long id) {
        return itemService.deleteItem(id);
    }
}
