package org.repro3d.service;

import org.repro3d.model.Item;
import org.repro3d.repository.ItemRepository;
import org.repro3d.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ResponseEntity<ApiResponse> createItem(Item item) {
        Item savedItem = itemRepository.save(item);
        return ResponseEntity.ok(new ApiResponse(true, "Item created successfully", savedItem));
    }

    public ResponseEntity<ApiResponse> getItemById(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            return ResponseEntity.ok(new ApiResponse(true, "Item found", item.get()));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "Item not found for ID: " + id, null));
        }
    }

    public ResponseEntity<ApiResponse> getByName(String name) {
        List<Item> items = itemRepository.findByName(name);
        if (!items.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse(true, "Items found with name: " + name, items));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "No items found with name: " + name, null));
        }
    }

    public ResponseEntity<ApiResponse> getByCreatorUserId(Long creatorUserId) {
        List<Item> items = itemRepository.findByCreatorUserId(creatorUserId);
        if (!items.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse(true, "Items found for creator user ID: " + creatorUserId, items));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "No items found for creator user ID: " + creatorUserId, null));
        }
    }

    public ResponseEntity<ApiResponse> getAllItems() {
        Iterable<Item> items = itemRepository.findAll();
        return ResponseEntity.ok(new ApiResponse(true, "Items retrieved successfully", items));
    }

    public ResponseEntity<ApiResponse> updateItem(Long id, Item itemDetails) {
        return itemRepository.findById(id)
                .map(item -> {
                    item.setName(itemDetails.getName());
                    item.setDescription(itemDetails.getDescription());
                    item.setCreator_user_id(itemDetails.getCreator_user_id());
                    item.setMaterial(itemDetails.getMaterial());
                    item.setBase_price(itemDetails.getBase_price());
                    item.setPath(itemDetails.getPath());
                    itemRepository.save(item);
                    return ResponseEntity.ok(new ApiResponse(true, "Item updated successfully", item));
                }).orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Item not found for ID: " + id, null)));
    }

    public ResponseEntity<ApiResponse> deleteItem(Long id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse(true, "Item deleted successfully", null));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "Item not found for ID: " + id, null));
        }
    }
}
