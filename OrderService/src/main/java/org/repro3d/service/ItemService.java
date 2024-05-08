package org.repro3d.service;

import org.repro3d.model.Item;
import org.repro3d.repository.ItemRepository;
import org.repro3d.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for handling operations related to {@link Item} entities.
 * This service encapsulates the business logic for creating, retrieving, updating, and deleting items,
 * interacting with the {@link ItemRepository} to perform these operations.
 */
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * Constructs an item service with the necessary item repository.
     *
     * @param itemRepository The repository used for item data operations.
     */
    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * Creates and saves a new item in the repository.
     *
     * @param item The item to be created and saved.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the result of the create operation.
     */
    public ResponseEntity<ApiResponse> createItem(Item item) {
        Item savedItem = itemRepository.save(item);
        return ResponseEntity.ok(new ApiResponse(true, "Item created successfully", savedItem));
    }

    /**
     * Retrieves an item by its ID.
     *
     * @param id The ID of the item to retrieve.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the item if found, or an error message otherwise.
     */
    public ResponseEntity<ApiResponse> getItemById(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            return ResponseEntity.ok(new ApiResponse(true, "Item found", item.get()));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "Item not found for ID: " + id, null));
        }
    }

    /**
     * Retrieves a list of items matching a given name.
     *
     * @param name The name to search for among items.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the list of found items or an error message if none found.
     */
    public ResponseEntity<ApiResponse> findByName(String name) {
        List<Item> items = itemRepository.findByName(name);
        if (!items.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse(true, "Items found with name: " + name, items));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "No items found with name: " + name, null));
        }
    }

    /**
     * Retrieves all items in the repository.
     *
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the list of all items.
     */
    public ResponseEntity<ApiResponse> getAllItems() {
        Iterable<Item> items = itemRepository.findAll();
        return ResponseEntity.ok(new ApiResponse(true, "Items retrieved successfully", items));
    }

    /**
     * Updates an existing item identified by its ID with new details.
     *
     * @param id          The ID of the item to update.
     * @param itemDetails The new details for the item.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the updated item, or an error message if the item was not found.
     */
    public ResponseEntity<ApiResponse> updateItem(Long id, Item itemDetails) {
        return itemRepository.findById(id)
                .map(item -> {
                    item.setName(itemDetails.getName());
                    item.setDescription(itemDetails.getDescription());
                    item.setEst_time(itemDetails.getEst_time());
                    item.setDimensions(itemDetails.getDimensions());
                    item.setFile_ref(itemDetails.getFile_ref());
                    item.setMaterial(itemDetails.getMaterial());
                    item.setCost(itemDetails.getCost());
                    item.setImage_url(itemDetails.getImage_url());
                    itemRepository.save(item);
                    return ResponseEntity.ok(new ApiResponse(true, "Item updated successfully", item));
                }).orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Item not found for ID: " + id, null)));
    }

    /**
     * Deletes an item from the repository identified by its ID.
     *
     * @param id The ID of the item to delete.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating the result of the delete operation.
     */
    public ResponseEntity<ApiResponse> deleteItem(Long id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse(true, "Item deleted successfully", null));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "Item not found for ID: " + id, null));
        }
    }
}
