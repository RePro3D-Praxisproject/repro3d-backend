package org.repro3d.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import repro3d.model.Order;
import repro3d.repository.OrderRepository;
import repro3d.service.OrderService;
import repro3d.utils.ApiResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setOrder_id(1L);
        order.setUser_id(1L);
        order.setRc_id(2L);
    }

    @Test
    void createOrderSuccessfully() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        ResponseEntity<ApiResponse> response = orderService.createOrder(order);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals(order, response.getBody().getData());
    }

    @Test
    void createOrderFailure() {
        when(orderRepository.save(any(Order.class))).thenThrow(new RuntimeException("Database error"));
        ResponseEntity<ApiResponse> response = orderService.createOrder(order);
        assertEquals(200, response.getStatusCodeValue());
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
    void getAllOrdersWithResult() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order));
        ResponseEntity<ApiResponse> response = orderService.getAllOrders();
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertNotNull(response.getBody().getData());
        List<Order> orders = (List<Order>) response.getBody().getData();
        assertFalse(orders.isEmpty());
    }

    @Test
    void getAllOrdersEmpty() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList());
        ResponseEntity<ApiResponse> response = orderService.getAllOrders();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isSuccess());
    }

    @Test
    void updateOrderFound() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        ResponseEntity<ApiResponse> response = orderService.updateOrder(1L, order);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals(order, response.getBody().getData());
    }

    @Test
    void updateOrderNotFound() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<ApiResponse> response = orderService.updateOrder(1L, order);
        assertEquals(200, response.getStatusCodeValue());
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
