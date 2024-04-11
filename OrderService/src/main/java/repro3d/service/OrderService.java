
package repro3d.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import repro3d.model.Order;
import repro3d.repository.OrderRepository;
import repro3d.utils.ApiResponse;

import java.util.List;
import java.util.Optional;


/**
 * Service class for handling operations related to {@link Order} entities.
 * Encapsulates the logic for creating, retrieving, updating, and deleting orders,
 * working in conjunction with the {@link OrderRepository}.
 */

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     * Constructs a new OrderService with the specified OrderRepository.
     *
     * @param orderRepository the order repository to be used
     */
    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Creates a new order.
     *
     * @param order the order to be created
     * @return ResponseEntity containing ApiResponse indicating success or failure of the operation
     */
    public ResponseEntity<ApiResponse> createOrder(Order order) {
        try {
            Order savedOrder = orderRepository.save(order);
            return ResponseEntity.ok(new ApiResponse(true, "Order created successfully.", savedOrder));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(false, "Failed to create order: " + e.getMessage(), null));
        }
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param id the ID of the order to be retrieved
     * @return ResponseEntity containing ApiResponse with the retrieved order or an error message if not found
     */
    public ResponseEntity<ApiResponse> getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(value -> ResponseEntity.ok(new ApiResponse(true, "Order found.", value)))
                .orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Order not found for ID: " + id, null)));
    }

    /**
     * Retrieves all orders.
     *
     * @return ResponseEntity containing ApiResponse with a list of orders or an error message if no orders found
     */
    public ResponseEntity<ApiResponse> getAllOrders() {
        try {
            List<Order> orders = orderRepository.findAll();
            if (orders.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse(false, "No orders found.", null));
            }
            return ResponseEntity.ok(new ApiResponse(true, "Orders retrieved successfully.", orders));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(false, "Failed to retrieve orders: " + e.getMessage(), null));
        }
    }

    /**
     * Updates an existing order.
     *
     * @param id the ID of the order to be updated
     * @param orderDetails the updated details of the order
     * @return ResponseEntity containing ApiResponse indicating success or failure of the operation
     */
    public ResponseEntity<ApiResponse> updateOrder(Long id, Order orderDetails) {
        return orderRepository.findById(id).map(order -> {
            order.setOrder_date(orderDetails.getOrder_date());
            order.setUser_id(orderDetails.getUser_id());
            order.setRc_id(orderDetails.getRc_id());
            Order updatedOrder = orderRepository.save(order);
            return ResponseEntity.ok(new ApiResponse(true, "Order updated successfully.", updatedOrder));
        }).orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Order not found for ID: " + id, null)));
    }

    /**
     * Deletes an order by its ID.
     *
     * @param id the ID of the order to be deleted
     * @return ResponseEntity containing ApiResponse indicating success or failure of the operation
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
