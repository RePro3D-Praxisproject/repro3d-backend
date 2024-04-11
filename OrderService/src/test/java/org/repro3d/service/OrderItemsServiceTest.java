package org.repro3d.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import repro3d.model.OrderItems;
import repro3d.repository.OrderItemsRepository;
import repro3d.service.OrderItemsService;
import repro3d.utils.ApiResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderItemsServiceTest {

    @Mock
    private OrderItemsRepository orderItemsRepository;

    @InjectMocks
    private OrderItemsService orderItemsService;

    private OrderItems orderItem;

    @BeforeEach
    void setUp() {
        orderItem = new OrderItems();
        orderItem.setOi_id(1L);
    }

    @Test
    void createOrderItemSuccessfully() {
        when(orderItemsRepository.save(any(OrderItems.class))).thenReturn(orderItem);
        ResponseEntity<ApiResponse> response = orderItemsService.createOrderItem(orderItem);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals(orderItem, response.getBody().getData());
    }

    @Test
    void getOrderItemByIdFound() {
        when(orderItemsRepository.findById(anyLong())).thenReturn(Optional.of(orderItem));
        ResponseEntity<ApiResponse> response = orderItemsService.getOrderItemById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals(orderItem, response.getBody().getData());
    }

    @Test
    void getOrderItemByIdNotFound() {
        when(orderItemsRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<ApiResponse> response = orderItemsService.getOrderItemById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isSuccess());
    }

    @Test
    void getAllOrderItemsWithResult() {
        when(orderItemsRepository.findAll()).thenReturn(Arrays.asList(orderItem));
        ResponseEntity<ApiResponse> response = orderItemsService.getAllOrderItems();
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertNotNull(response.getBody().getData());
        List<OrderItems> items = (List<OrderItems>) response.getBody().getData();
        assertFalse(items.isEmpty());
    }

    @Test
    void getAllOrderItemsEmpty() {
        when(orderItemsRepository.findAll()).thenReturn(Arrays.asList());
        ResponseEntity<ApiResponse> response = orderItemsService.getAllOrderItems();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isSuccess());
    }

    @Test
    void updateOrderItemFound() {
        when(orderItemsRepository.findById(anyLong())).thenReturn(Optional.of(orderItem));
        when(orderItemsRepository.save(any(OrderItems.class))).thenReturn(orderItem);
        ResponseEntity<ApiResponse> response = orderItemsService.updateOrderItem(1L, orderItem);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals(orderItem, response.getBody().getData());
    }

    @Test
    void updateOrderItemNotFound() {
        when(orderItemsRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<ApiResponse> response = orderItemsService.updateOrderItem(1L, orderItem);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isSuccess());
    }

    @Test
    void deleteOrderItemFound() {
        when(orderItemsRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(orderItemsRepository).deleteById(anyLong());
        ResponseEntity<ApiResponse> response = orderItemsService.deleteOrderItem(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
    }

    @Test
    void deleteOrderItemNotFound() {
        when(orderItemsRepository.existsById(anyLong())).thenReturn(false);
        ResponseEntity<ApiResponse> response = orderItemsService.deleteOrderItem(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isSuccess());
    }
}
