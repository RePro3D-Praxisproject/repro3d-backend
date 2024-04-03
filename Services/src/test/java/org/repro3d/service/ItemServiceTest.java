package org.repro3d.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.repro3d.model.Item;
import org.repro3d.repository.ItemRepository;
import org.repro3d.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import app.getxray.xray.junit.customjunitxml.annotations.XrayTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    private Item item;

    /**
     * Sets up common test objects and configurations before each test.
     */
    @BeforeEach
    void setUp() {
        // Initialize a sample item to be used in multiple tests
        item = new Item();
        item.setItem_id(1L);
        item.setName("Sample Item");
        item.setDescription("This is a sample item");
        item.setCreator_user_id(0L);
        item.setMaterial("Plastic");
        item.setBase_price(10.0);
        item.setPath("/models/sample-item.gcode");
    }

    /**
     * Tests the item creation process to ensure the item is successfully saved and the correct response is returned.
     */
    @Test
    void testCreateItem() {
        // Mocking the repository to return the sample item when saving
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        ResponseEntity<ApiResponse> response = itemService.createItem(item);

        // Verifying the save operation was called once and the response is as expected
        verify(itemRepository, times(1)).save(any(Item.class));
        assert(response.getBody().isSuccess());
        assert("Item created successfully".equals(response.getBody().getMessage()));
    }

    /**
     * Tests retrieving an item by ID when the item exists.
     */
    @Test
    @XrayTest(key = "RP-36", id = "RP-36")
    void testGetItemByIdFound() {
        // Mocking the repository to return an Optional containing the item
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));

        ResponseEntity<ApiResponse> response = itemService.getItemById(1L);

        // Assertions to ensure the correct item is returned
        assert(response.getBody().isSuccess());
        assert("Item found".equals(response.getBody().getMessage()));
    }

    /**
     * Tests retrieving an item by ID when the item does not exist.
     */
    @Test
    void testGetItemByIdNotFound() {
        // Mocking the repository to return an empty Optional
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = itemService.getItemById(1L);

        // Assertions to check the appropriate not found response is returned
        assert(!response.getBody().isSuccess());
        assert("Item not found for ID: 1".equals(response.getBody().getMessage()));
    }

    /**
     * Tests retrieving items by name when at least one item with the specified name exists.
     */
    @Test
    void testGetByNameFound() {
        // Mocking the repository to return a list containing the item when searched by name
        List<Item> itemList = Arrays.asList(item);
        when(itemRepository.findByName(anyString())).thenReturn(itemList);

        ResponseEntity<ApiResponse> response = itemService.getByName("Sample Item");

        // Verifying that the search returns a success response with the correct message
        assert(response.getBody().isSuccess());
        assert("Items found with name: Sample Item".equals(response.getBody().getMessage()));
    }

    /**
     * Tests retrieving items by name when no items with the specified name exist.
     */
    @Test
    void testGetByNameNotFound() {
        // Mocking the repository to return an empty list when searched by name
        when(itemRepository.findByName(anyString())).thenReturn(Arrays.asList());

        ResponseEntity<ApiResponse> response = itemService.getByName("Nonexistent Item");

        // Verifying that the search returns a response indicating no items were found
        assert(!response.getBody().isSuccess());
        assert("No items found with name: Nonexistent Item".equals(response.getBody().getMessage()));
    }

    /**
     * Tests retrieving items by creator user ID when at least one item created by the specified user exists.
     */
    @Test
    void testGetByCreatorUserIdFound() {
        // Mock setup to return a list of items for a given creator user ID
        List<Item> itemList = Arrays.asList(item);
        when(itemRepository.findByCreatorUserId(anyLong())).thenReturn(itemList);

        ResponseEntity<ApiResponse> response = itemService.getByCreatorUserId(0L);

        // Asserting successful retrieval and correct message
        assert(response.getBody().isSuccess());
        assert("Items found for creator user ID: 0".equals(response.getBody().getMessage()));
    }

    /**
     * Tests retrieving items by creator user ID when no items created by the specified user exist.
     */
    @Test
    void testGetByCreatorUserIdNotFound() {
        // Mock setup to return an empty list for a given creator user ID
        when(itemRepository.findByCreatorUserId(anyLong())).thenReturn(Arrays.asList());

        ResponseEntity<ApiResponse> response = itemService.getByCreatorUserId(0L);

        // Asserting the response indicates no items were found
        assert(!response.getBody().isSuccess());
        assert("No items found for creator user ID: 0".equals(response.getBody().getMessage()));
    }

    /**
     * Tests retrieving all items.
     */
    @Test
    void testGetAllItems() {
        // Mocking to return a list of all items
        when(itemRepository.findAll()).thenReturn(Arrays.asList(item));

        ResponseEntity<ApiResponse> response = itemService.getAllItems();

        // Verifying successful retrieval of all items
        assert(response.getBody().isSuccess());
        assert("Items retrieved successfully".equals(response.getBody().getMessage()));
    }

    /**
     * Tests updating an item when the item exists.
     */
    @Test
    void testUpdateItemFound() {
        // Setup for mocking to find an item and save the updated item
        Item updatedItem = new Item(1L, "Updated Name", "Updated Description", 100L, "Metal", 20.00, "/models/updated-item.gcode");
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class))).thenReturn(updatedItem);

        ResponseEntity<ApiResponse> response = itemService.updateItem(1L, updatedItem);

        // Verifying the item was saved and the correct response message is returned
        verify(itemRepository).save(item);
        assert(response.getBody().isSuccess());
        assert("Item updated successfully".equals(response.getBody().getMessage()));
    }

    /**
     * Tests updating an item when the item does not exist.
     */
    @Test
    void testUpdateItemNotFound() {
        // Mocking to return an empty Optional when the item is not found
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = itemService.updateItem(1L, new Item());

        // Asserting that the update operation returns a not found response
        assert(!response.getBody().isSuccess());
        assert("Item not found for ID: 1".equals(response.getBody().getMessage()));
    }

    /**
     * Tests deleting an item when the item exists.
     */
    @Test
    void testDeleteItemFound() {
        // Mocking the existence check to return true and setup for delete operation
        when(itemRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(itemRepository).deleteById(anyLong());

        ResponseEntity<ApiResponse> response = itemService.deleteItem(1L);

        // Verifying the delete operation was called and the correct response message is returned
        verify(itemRepository, times(1)).deleteById(anyLong());
        assert(response.getBody().isSuccess());
        assert("Item deleted successfully".equals(response.getBody().getMessage()));
    }

    /**
     * Tests deleting an item when the item does not exist.
     */
    @Test
    void testDeleteItemNotFound() {
        // Mocking the existence check to return false
        when(itemRepository.existsById(anyLong())).thenReturn(false);

        ResponseEntity<ApiResponse> response = itemService.deleteItem(1L);

        // Asserting the delete operation indicates the item was not found
        assert(!response.getBody().isSuccess());
        assert("Item not found for ID: 1".equals(response.getBody().getMessage()));
    }
}
