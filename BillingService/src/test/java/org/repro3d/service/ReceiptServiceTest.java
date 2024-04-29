package org.repro3d.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.repro3d.model.Order;
import org.repro3d.model.Receipt;
import org.repro3d.repository.OrderRepository;
import org.repro3d.repository.ReceiptRepository;
import org.repro3d.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ReceiptServiceTest {

    @Mock
    private ReceiptRepository receiptRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private ReceiptService receiptService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createReceipt_OrderExists_ReceiptCreated() {
        Receipt receipt = new Receipt();
        Order order = new Order(1L, new Date(), null, null);
        receipt.setOrder(order);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(receiptRepository.save(any(Receipt.class))).thenReturn(receipt);

        ResponseEntity<ApiResponse> response = receiptService.createReceipt(receipt);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals(receipt, response.getBody().getData());
    }

    @Test
    void createReceipt_OrderNotExists_ReturnBadRequest() {
        Receipt receipt = new Receipt();
        receipt.setOrder(new Order(1L, new Date(), null, null));
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = receiptService.createReceipt(receipt);

        assertEquals(400, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertNull(response.getBody().getData());
    }

    @Test
    void getReceiptById_ReceiptExists_ReturnReceipt() {
        Long receiptId = 1L;
        Receipt receipt = new Receipt();
        when(receiptRepository.findById(receiptId)).thenReturn(Optional.of(receipt));

        ResponseEntity<ApiResponse> response = receiptService.getReceiptById(receiptId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals(receipt, response.getBody().getData());
    }

    @Test
    void getReceiptById_ReceiptNotExists_ReturnNotFoundResponse() {
        Long receiptId = 1L;
        when(receiptRepository.findById(receiptId)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = receiptService.getReceiptById(receiptId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertNull(response.getBody().getData());
    }

    @Test
    void getAllReceipts_ReceiptsExist_ReturnAllReceipts() {
        List<Receipt> receipts = Arrays.asList(new Receipt(), new Receipt());
        when(receiptRepository.findAll()).thenReturn(receipts);

        ResponseEntity<ApiResponse> response = receiptService.getAllReceipts();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals(receipts, response.getBody().getData());
    }

    @Test
    void updateReceipt_ReceiptAndOrderExist_ReceiptUpdated() {
        Long receiptId = 1L;
        Receipt existingReceipt = new Receipt();
        existingReceipt.setOrder(new Order(1L, new Date(), null, null));
        Receipt newReceiptDetails = new Receipt();
        newReceiptDetails.setOrder(new Order(1L, new Date(), null, null));
        when(receiptRepository.findById(receiptId)).thenReturn(Optional.of(existingReceipt));
        when(orderRepository.findById(1L)).thenReturn(Optional.of(new Order()));
        when(receiptRepository.save(any(Receipt.class))).thenReturn(newReceiptDetails);

        ResponseEntity<ApiResponse> response = receiptService.updateReceipt(receiptId, newReceiptDetails);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals(newReceiptDetails, response.getBody().getData());
    }

    @Test
    void updateReceipt_OrderNotExists_ReturnBadRequest() {
        Long receiptId = 1L;
        Receipt receiptDetails = new Receipt();
        receiptDetails.setOrder(new Order(1L, new Date(), null, null));
        when(receiptRepository.findById(receiptId)).thenReturn(Optional.of(new Receipt()));
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = receiptService.updateReceipt(receiptId, receiptDetails);

        assertEquals(400, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
    }

    @Test
    void updateReceipt_ReceiptNotExists_ReturnNotFoundResponse() {
        Long receiptId = 1L;
        when(receiptRepository.findById(receiptId)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = receiptService.updateReceipt(receiptId, new Receipt());

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
    }

    @Test
    void deleteReceipt_ReceiptExists_ReceiptDeleted() {
        Long receiptId = 1L;
        when(receiptRepository.existsById(receiptId)).thenReturn(true);
        doNothing().when(receiptRepository).deleteById(receiptId);

        ResponseEntity<ApiResponse> response = receiptService.deleteReceipt(receiptId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
    }

    @Test
    void deleteReceipt_ReceiptNotExists_ReturnNotFoundResponse() {
        Long receiptId = 1L;
        when(receiptRepository.existsById(receiptId)).thenReturn(false);

        ResponseEntity<ApiResponse> response = receiptService.deleteReceipt(receiptId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
    }
}
