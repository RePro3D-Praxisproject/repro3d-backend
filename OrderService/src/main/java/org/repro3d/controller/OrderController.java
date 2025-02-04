package org.repro3d.controller;

import org.repro3d.model.*;
import org.repro3d.service.OrderItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.repro3d.service.OrderService;
import org.repro3d.utils.ApiResponse;
import java.util.Objects;

/**
 * Controller for managing {@link Order} entities.
 * Provides endpoints for CRUD operations on orders, including creation, retrieval,
 * updating, and deletion of orders.
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderItemsService orderItemService;

    /**
     * Constructs an {@code OrderController} with the necessary {@link OrderService}.
     * @param orderService The service used to perform operations on orders.
     */
    @Autowired
    public OrderController(OrderService orderService, OrderItemsService orderItemService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    /**
     * Creates a new order.
     * @param order The order to be created.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
     *         the result of the operation.
     */
    @PostMapping
    public ResponseEntity<ApiResponse> createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    /**
     * Creates an order and creates the order items and jobs for it.
     * @param po {@link PlaceOrder} Custom request body.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
      *         the result of the operation.
     */
    @PostMapping("/place")
    public ResponseEntity<ApiResponse> placeOrder(@RequestBody PlaceOrder po) {
        ResponseEntity<ApiResponse> order = orderService.createOrder(po.getOrder());
        for (Item item : po.getItems()) {

            Job j = new Job();
            j.setItem(item);
            j.setStart_date(po.getOrder().getOrderDate());
            j.setStatus(new Status(1L, "Waiting"));


            OrderItems oi = new OrderItems();
            oi.setItem(item);
            oi.setOrder((Order) Objects.requireNonNull(order.getBody()).getData());


            orderItemService.placeOrderItem(oi, j);
        }
        return order;
    }

    /**
     * Retrieves an order by its ID.
     * @param id The ID of the order to retrieve.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the order
     *         if found, or an error message otherwise.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    /**
     * Retrieves all orders.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with a list
     *         of all orders.
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    /**
     * Updates the details of an existing order.
     * @param id The ID of the order to update.
     * @param order The new details for the order.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
     *         the result of the update operation.
     */
    /**
     * Retrieves all orders for a user by their email.
     * @param email The email of the user whose orders are to be retrieved.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with a list
     *         of all orders for the user if found, or an error message otherwise.
     */
    @GetMapping("/email")
    public ResponseEntity<ApiResponse> getOrdersByEmail(@RequestParam String email) {
        return orderService.getOrdersByEmail(email);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }

    /**
     * Deletes an order by its ID.
     * @param id The ID of the order to delete.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
     *         the result of the delete operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrder(id);
    }
}
