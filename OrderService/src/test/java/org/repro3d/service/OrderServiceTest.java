package org.repro3d.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.repro3d.model.Order;
import org.repro3d.model.RedeemCode;
import org.repro3d.model.User;
import org.repro3d.repository.OrderRepository;
import org.repro3d.repository.RedeemCodeRepository;
import org.repro3d.repository.UserRepository;
import org.repro3d.utils.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RedeemCodeRepository redeemCodeRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private User user;
    private RedeemCode redeemCode;

    @BeforeEach
    void setUp() {
        user = new User(1L, "user@example.com", "1234 Street", null);
        redeemCode = new RedeemCode(2L, "SOME_CODE", false);
        order = new Order(1L, null, user, redeemCode);
    }

    @Test
    void createOrderSuccessfully() {
        when(userRepository.existsById(user.getUserId())).thenReturn(true);
        when(redeemCodeRepository.existsById(redeemCode.getRc_id())).thenReturn(true);
        when(redeemCodeRepository.findById(redeemCode.getRc_id())).thenReturn(Optional.ofNullable(redeemCode));

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        ResponseEntity<ApiResponse> response = orderService.createOrder(order);
        System.out.println(response.getBody().getMessage());
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals(order, response.getBody().getData());

    }

    @Test
    void createOrderFailureUserNotFound() {
        when(userRepository.existsById(anyLong())).thenReturn(false);

        ResponseEntity<ApiResponse> response = orderService.createOrder(order);

        assertEquals(400, response.getStatusCodeValue());
        assertFalse(response.getBody().isSuccess());
    }

    @Test
    void createOrderFailureRedeemCodeNotFound() {
        when(userRepository.existsById(user.getUserId())).thenReturn(true);
        when(redeemCodeRepository.existsById(redeemCode.getRc_id())).thenReturn(false);

        ResponseEntity<ApiResponse> response = orderService.createOrder(order);

        assertEquals(400, response.getStatusCodeValue());
        assertFalse(response.getBody().isSuccess());
    }

    @Test
    void createOrderFailureRedeemCodeUsed() {
        redeemCode.setUsed(true);
        when(userRepository.existsById(user.getUserId())).thenReturn(true);
        when(redeemCodeRepository.existsById(redeemCode.getRc_id())).thenReturn(true);
        lenient().when(redeemCodeRepository.findById(redeemCode.getRc_id())).thenReturn(Optional.of(redeemCode));

        ResponseEntity<ApiResponse> response = orderService.createOrder(order);

        assertEquals(400, response.getStatusCodeValue());
        assertFalse(response.getBody().isSuccess());
    }

    @Test
    void getOrderByIdFound() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        ResponseEntity<ApiResponse> response = orderService.getOrderById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals(order, response.getBody().getData());
    }

    @Test
    void getOrderByIdNotFound() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = orderService.getOrderById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isSuccess());
    }

    @Test
    void updateOrderFoundAndValid() {
        lenient().when(orderRepository.findById((order.getOrder_id()))).thenReturn(Optional.of(order));
        lenient().when(userRepository.existsById(user.getUserId())).thenReturn(true);
        lenient().when(redeemCodeRepository.existsById(redeemCode.getRc_id())).thenReturn(true);
        lenient().when(redeemCodeRepository.findById(redeemCode.getRc_id())).thenReturn(Optional.of(redeemCode));
        lenient().when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        lenient().when(orderRepository.save(any(Order.class))).thenReturn(order);
        ResponseEntity<ApiResponse> response = orderService.updateOrder(1L, order);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals(order, response.getBody().getData());
    }

    @Test
    void updateOrderNotFound() {
        ResponseEntity<ApiResponse> response = orderService.updateOrder(1L, order);

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isSuccess());
    }

    @Test
    void updateOrderFailureRedeemCodeUsed() {
        redeemCode.setUsed(true);
        lenient().when(orderRepository.findById((order.getOrder_id()))).thenReturn(Optional.of(order));
        lenient().when(userRepository.existsById(user.getUserId())).thenReturn(true);
        lenient().when(redeemCodeRepository.existsById(redeemCode.getRc_id())).thenReturn(true);
        when(redeemCodeRepository.findById(redeemCode.getRc_id())).thenReturn(Optional.of(redeemCode));
        ResponseEntity<ApiResponse> response = orderService.updateOrder(1L, order);

        assertEquals(400, response.getStatusCodeValue());
        assertFalse(response.getBody().isSuccess());
    }

    @Test
    void deleteOrderFound() {
        when(orderRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(orderRepository).deleteById(anyLong());

        ResponseEntity<ApiResponse> response = orderService.deleteOrder(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
    }

    @Test
    void deleteOrderNotFound() {
        when(orderRepository.existsById(anyLong())).thenReturn(false);

        ResponseEntity<ApiResponse> response = orderService.deleteOrder(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isSuccess());
    }
}
