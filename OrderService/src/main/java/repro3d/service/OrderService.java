package repro3d.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import repro3d.model.Order;
import repro3d.repository.OrderRepository;
import repro3d.repository.UserRepository;
import repro3d.utils.ApiResponse;

import java.util.List;
import java.util.Optional;

/**
 * Service class for handling operations related to {@link Order} entities.
 * Encapsulates the logic for creating, retrieving, updating, and deleting orders,
 * ensuring that all related entities such as User exist before processing.
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    /**
     * Constructs a new OrderService with the specified OrderRepository and UserRepository.
     *
     * @param orderRepository The repository for managing {@link Order} entities.
     * @param userRepository  The repository for managing {@link User} entities.
     */
    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    /**
     * Creates a new order, checking if the associated User exists.
     *
     * @param order The order to be created.
     * @return ResponseEntity containing ApiResponse indicating the outcome.
     */
    public ResponseEntity<ApiResponse> createOrder(Order order) {
        if (order.getUser() != null && userRepository.existsById(order.getUser().getUserId())) {
            Order savedOrder = orderRepository.save(order);
            return ResponseEntity.ok(new ApiResponse(true, "Order created successfully.", savedOrder));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "User not found for ID: " + (order.getUser() != null ? order.getUser().getUserId() : null), null));
        }
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param id The ID of the order to retrieve.
     * @return ResponseEntity containing ApiResponse with the order or an error message.
     */
    public ResponseEntity<ApiResponse> getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(value -> ResponseEntity.ok(new ApiResponse(true, "Order found.", value)))
                .orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Order not found for ID: " + id, null)));
    }

    /**
     * Retrieves all orders.
     *
     * @return ResponseEntity containing ApiResponse with all orders or an error message.
     */
    public ResponseEntity<ApiResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        if (!orders.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse(true, "Orders retrieved successfully.", orders));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "No orders found.", null));
        }
    }

    /**
     * Updates an existing order with the provided details, ensuring the User exists.
     *
     * @param id          The ID of the order to update.
     * @param orderDetails The new details for the order.
     * @return ResponseEntity containing ApiResponse indicating the outcome.
     */
    public ResponseEntity<ApiResponse> updateOrder(Long id, Order orderDetails) {
        return orderRepository.findById(id).map(order -> {
            if (userRepository.existsById(orderDetails.getUser().getUserId())) {
                order.setOrderDate(orderDetails.getOrderDate());
                order.setUser(orderDetails.getUser());
                order.setRcId(orderDetails.getRcId());
                Order updatedOrder = orderRepository.save(order);
                return ResponseEntity.ok(new ApiResponse(true, "Order updated successfully.", updatedOrder));
            } else {
                return ResponseEntity.badRequest().body(new ApiResponse(false, "User not found for ID: " + orderDetails.getUser().getUserId(), null));
            }
        }).orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Order not found for ID: " + id, null)));
    }

    /**
     * Deletes an order by its ID.
     *
     * @param id The ID of the order to delete.
     * @return ResponseEntity containing ApiResponse indicating the outcome.
     */
    public ResponseEntity<ApiResponse> deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse(true, "Order deleted successfully.", null));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "Order not found for ID: " + id, null));
        }
    }
}
