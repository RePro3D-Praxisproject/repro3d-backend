package repro3d.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import repro3d.model.OrderItems;
import repro3d.repository.JobRepository;
import repro3d.repository.OrderItemsRepository;
import repro3d.repository.OrderRepository;
import repro3d.utils.ApiResponse;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing operations related to OrderItems entities.
 * Handles creation, retrieval, update, and deletion of order items,
 * working with the OrderItemsRepository.
 */
@Service
public class OrderItemsService {

    private final OrderItemsRepository orderItemsRepository;
    private final JobRepository jobRepository;
    private final OrderRepository orderRepository;

    /**
     * Initializes a new instance of the OrderItemsService with the required repositories.
     *
     * @param orderItemsRepository The repository used for data operations on order items.
     * @param jobRepository The repository used for accessing job data.
     * @param orderRepository The repository used for accessing order data.
     */
    @Autowired
    public OrderItemsService(OrderItemsRepository orderItemsRepository, JobRepository jobRepository, OrderRepository orderRepository) {
        this.orderItemsRepository = orderItemsRepository;
        this.jobRepository = jobRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * Creates and saves a new order item in the database after verifying that all associated entities exist.
     *
     * @param orderItems The order item to be created.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the result of the create operation.
     */
    public ResponseEntity<ApiResponse> createOrderItem(OrderItems orderItems) {
        boolean jobExists = orderItems.getJob() != null && jobRepository.existsById(orderItems.getJob().getJob_id());
        boolean orderExists = orderRepository.existsById(orderItems.getOrder().getOrder_id());

        if (!jobExists || !orderExists) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Job or Order does not exist, cannot create OrderItem", null));
        }

        OrderItems savedOrderItems = orderItemsRepository.save(orderItems);
        return ResponseEntity.ok(new ApiResponse(true, "Order item created successfully.", savedOrderItems));
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
     * Retrieves an order item by its ID.
     *
     * @param id The ID of the order item to retrieve.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the order item, if found.
     */
    public ResponseEntity<ApiResponse> getOrderItemById(Long id) {
        Optional<OrderItems> orderItem = orderItemsRepository.findById(id);
        if (orderItem.isPresent()) {
            return ResponseEntity.ok(new ApiResponse(true, "Order item retrieved successfully.", orderItem.get()));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "Order item not found for ID: " + id, null));
        }
    }


    /**
     * Updates an existing order item with new details after verifying referenced entities.
     *
     * @param id The ID of the order item to update.
     * @param orderItemsDetails New details to update the order item with.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the result of the update operation.
     */
    public ResponseEntity<ApiResponse> updateOrderItem(Long id, OrderItems orderItemsDetails) {
        return orderItemsRepository.findById(id).map(orderItems -> {
            if (jobRepository.existsById(orderItemsDetails.getJob().getJob_id()) &&
                    orderRepository.existsById(orderItemsDetails.getOrder().getOrder_id())) {
                orderItems.setItem(orderItemsDetails.getItem());
                orderItems.setJob(orderItemsDetails.getJob());
                orderItems.setOrder(orderItemsDetails.getOrder());
                OrderItems updatedOrderItems = orderItemsRepository.save(orderItems);
                return ResponseEntity.ok(new ApiResponse(true, "Order item updated successfully.", updatedOrderItems));
            } else {
                return ResponseEntity.badRequest().body(new ApiResponse(false, "Referenced Job or Order does not exist", null));
            }
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
