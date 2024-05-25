package org.repro3d.service;

import org.repro3d.repository.RedeemCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.repro3d.model.Order;
import org.repro3d.model.User;
import org.repro3d.model.RedeemCode;
import org.repro3d.repository.OrderRepository;
import org.repro3d.repository.UserRepository;
import org.repro3d.utils.ApiResponse;

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

    private final RedeemCodeRepository redeemCodeRepository;



    /**
     * Constructs a new OrderService with the specified OrderRepository and UserRepository.
     *
     * @param orderRepository The repository for managing {@link Order} entities.
     * @param userRepository  The repository for managing {@link User} entities.
     * @param redeemCodeRepository  The repository for managing {@link RedeemCode} entities.
     */
    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, RedeemCodeRepository redeemCodeRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.redeemCodeRepository = redeemCodeRepository;
    }

    /**
     * Creates a new order, checking if the associated User and RedeemCode exist.
     *
     * @param order The order to be created.
     * @return ResponseEntity containing ApiResponse indicating the outcome.
     */
    public ResponseEntity<ApiResponse> createOrder(Order order) {
        if (order.getUser() == null || !userRepository.existsById(order.getUser().getUserId())) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "User not found for ID: " + (order.getUser() != null ? order.getUser().getUserId() : "null"), null));
        }

        if (order.getRedeemCode() == null || !redeemCodeRepository.findByRcCode(order.getRedeemCode().getRcCode()).isPresent()) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Redeem code not found", null));
        }

        RedeemCode redeemCode = redeemCodeRepository.findByRcCode(order.getRedeemCode().getRcCode()).orElse(null);
        if (redeemCode == null || redeemCode.getUsed()) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid or already used redeem code.", null));
        }

        redeemCode.setUsed(true);
        redeemCodeRepository.save(redeemCode);
        
        order.setRedeemCode(redeemCode);
        Order savedOrder = orderRepository.save(order);
        return ResponseEntity.ok(new ApiResponse(true, "Order created successfully.", savedOrder));
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
     * Updates an existing order with the provided details, ensuring the User and RedeemCode exists (and RC has not been used).
     *
     * @param id          The ID of the order to update.
     * @param orderDetails The new details for the order.
     * @return ResponseEntity containing ApiResponse indicating the outcome.
     */
    public ResponseEntity<ApiResponse> updateOrder(Long id, Order orderDetails) {
        return orderRepository.findById(id).map(order -> {
            if (orderDetails.getUser() != null && userRepository.existsById(orderDetails.getUser().getUserId())) {
                order.setOrderDate(orderDetails.getOrderDate());
                order.setUser(orderDetails.getUser());

                if (orderDetails.getRedeemCode() != null) {
                    Optional<RedeemCode> redeemCodeOpt = redeemCodeRepository.findById(orderDetails.getRedeemCode().getRc_id()); // create optional redeem code object (by getting it from the repository with da id)
                    if (!redeemCodeOpt.isPresent()) {
                        return ResponseEntity.badRequest().body(new ApiResponse(false, "Redeem code not found for ID: " + orderDetails.getRedeemCode().getRc_id(), null));
                    } else if (redeemCodeOpt.get().getUsed()) {
                        return ResponseEntity.badRequest().body(new ApiResponse(false, "Redeem code already used.", null));
                    }
                    order.setRedeemCode(redeemCodeOpt.get());
                } else {
                    return ResponseEntity.badRequest().body(new ApiResponse(false, "Redeem code not found for ID: " + orderDetails.getRedeemCode().getRc_id(), null));
                }

                Order updatedOrder = orderRepository.save(order);
                return ResponseEntity.ok(new ApiResponse(true, "Order updated successfully.", updatedOrder));
            } else {
                return ResponseEntity.badRequest().body(new ApiResponse(false, "User not found for ID: " + (orderDetails.getUser() != null ? orderDetails.getUser().getUserId() : null), null));
            }
        }).orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Order not found for ID: " + id, null)));
    }

    /**
     * Retrieves all orders for a user by their email.
     * @param email The email of the user whose orders are to be retrieved.
     * @return ResponseEntity containing ApiResponse with the orders or an error message.
     */
    public ResponseEntity<ApiResponse> getOrdersByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            return ResponseEntity.ok(new ApiResponse(false, "User not found with email: " + email, null));
        }

        List<Order> orders = orderRepository.findAllByUser(user.get());
        if (orders.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse(false, "No orders found for user with email: " + email, null));
        } else {
            return ResponseEntity.ok(new ApiResponse(true, "Orders retrieved successfully for user with email: " + email, orders));
        }
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
