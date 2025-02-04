package org.repro3d.controller;

import org.repro3d.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.repro3d.model.OrderItems;
import org.repro3d.service.OrderItemsService;
import org.repro3d.utils.ApiResponse;

/**
 * Controller for managing {@link OrderItems} entities.
 * Provides endpoints for CRUD operations on order items, facilitating the management
 * of items within orders, including creation, retrieval, updating, and deletion.
 */
@RestController
@RequestMapping("/api/order-item")
public class OrderItemsController {

    private final OrderItemsService orderItemsService;

    /**
     * Constructs an {@code OrderItemsController} with the necessary {@link OrderItemsService}.
     * This service is used to perform operations on order items.
     * @param orderItemsService The service used for order item operations.
     */
    @Autowired
    public OrderItemsController(OrderItemsService orderItemsService) {
        this.orderItemsService = orderItemsService;
    }

    /**
     * Creates a new order item.
     * @param orderItems The order item to be created.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
     *         the result of the creation operation.
     */
    @PostMapping
    public ResponseEntity<ApiResponse> createOrderItem(@RequestBody OrderItems orderItems) {
        return orderItemsService.createOrderItem(orderItems);
    }

    /**
     * Retrieves an order item by its ID.
     * @param id The ID of the order item to retrieve.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the order item
     *         if found, or an error message otherwise.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getOrderItemById(@PathVariable Long id) {
        return orderItemsService.getOrderItemById(id);
    }

    /**
     * Retrieves all order items.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with a list
     *         of all order items.
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getAllOrderItems() {
        return orderItemsService.getAllOrderItems();
    }

    /**
     * Retrieves all order_id items by order_id.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with a list
     *         of all order_id items belonging to that order_id.
     */
    @GetMapping("/by-order/{order_id}")
    public ResponseEntity<ApiResponse> getAllOrderItemsByOrderId(@PathVariable Order order_id) {
        return orderItemsService.getAllOrderItemsByOrder(order_id);
    }

    /**
     * Updates the details of an existing order item.
     * @param id The ID of the order item to update.
     * @param orderItemsDetails The new details for the order item.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
     *         the result of the update operation.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateOrderItem(@PathVariable Long id, @RequestBody OrderItems orderItemsDetails) {
        return orderItemsService.updateOrderItem(id, orderItemsDetails);
    }

    /**
     * Deletes an order item by its ID.
     * @param id The ID of the order item to delete.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
     *         the result of the deletion operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteOrderItem(@PathVariable Long id) {
        return orderItemsService.deleteOrderItem(id);
    }


}
