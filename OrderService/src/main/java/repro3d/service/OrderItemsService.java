package repro3d.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import repro3d.model.OrderItems;
import repro3d.repository.OrderItemsRepository;
import repro3d.utils.ApiResponse;

import java.util.List;
import java.util.Optional;

/**
 * Service class for handling operations related to {@link OrderItems} entities.
 * Encapsulates the logic for creating, retrieving, updating, and deleting order items,
 * working in conjunction with the {@link OrderItemsRepository}.
 */
@Service
public class OrderItemsService {

    private final OrderItemsRepository orderItemsRepository;

    /**
     * Constructs an order items service with the necessary repository.
     *
     * @param orderItemsRepository The repository used for data operations on order items.
     */
    @Autowired
    public OrderItemsService(OrderItemsRepository orderItemsRepository) {
        this.orderItemsRepository = orderItemsRepository;
    }

    /**
     * Creates and saves a new order item in the database.
     *
     * @param orderItems The order item to be created.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the result of the create operation.
     */
    public ResponseEntity<ApiResponse> createOrderItem(OrderItems orderItems) {
        OrderItems savedOrderItems = orderItemsRepository.save(orderItems);
        return ResponseEntity.ok(new ApiResponse(true, "Order item created successfully.", savedOrderItems));
    }

    /**
     * Retrieves an order item by its ID.
     *
     * @param id The ID of the order item to retrieve.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the order item if found, or an error message otherwise.
     */
    public ResponseEntity<ApiResponse> getOrderItemById(Long id) {
        Optional<OrderItems> orderItems = orderItemsRepository.findById(id);
        if (orderItems.isPresent()) {
            return ResponseEntity.ok(new ApiResponse(true, "Order item found.", orderItems.get()));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "Order item not found for ID: " + id, null));
        }
    }

    /**
     * Retrieves all order items from the database.
     *
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with a list of all order items.
     */
    public ResponseEntity<ApiResponse> getAllOrderItems() {
        List<OrderItems> orderItems = orderItemsRepository.findAll();
        if (!orderItems.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse(true, "Order items retrieved successfully.", orderItems));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "No order items found.", null));
        }
    }

    /**
     * Updates an existing order item with new details.
     *
     * @param id The ID of the order item to update.
     * @param orderItemsDetails New details to update the order item with.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the result of the update operation.
     */
    public ResponseEntity<ApiResponse> updateOrderItem(Long id, OrderItems orderItemsDetails) {
        return orderItemsRepository.findById(id).map(orderItems -> {
            orderItems.setItem(orderItemsDetails.getItem());
            orderItems.setJob_id(orderItemsDetails.getJob_id());
            orderItems.setOrder(orderItemsDetails.getOrder());
            OrderItems updatedOrderItems = orderItemsRepository.save(orderItems);
            return ResponseEntity.ok(new ApiResponse(true, "Order item updated successfully.", updatedOrderItems));
        }).orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Order item not found for ID: " + id, null)));
    }

    /**
     * Deletes an order item from the database by its ID.
     *
     * @param id The ID of the order item to delete.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating the result of the delete operation.
     */
    public ResponseEntity<ApiResponse> deleteOrderItem(Long id) {
        if (orderItemsRepository.existsById(id)) {
            orderItemsRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse(true, "Order item deleted successfully.", null));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "Order item not found for ID: " + id, null));
        }
    }
}
