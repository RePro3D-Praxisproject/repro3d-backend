package org.repro3d.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.repro3d.model.Printer;
import org.repro3d.repository.PrinterRepository;
import org.springframework.http.ResponseEntity;
import org.repro3d.utils.ApiResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PrinterServiceTest {

    @Mock
    private PrinterRepository printerRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private PrinterService printerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPrinter_Successful() {
        Printer printer = new Printer(1L, "Printer1", "Room 101", "192.168.1.1", "12345apikey");
        when(printerRepository.save(any(Printer.class))).thenReturn(printer);

        ResponseEntity<ApiResponse> response = printerService.createPrinter(printer);

        assertTrue(response.getBody().isSuccess());
        assertEquals("Printer created successfully.", response.getBody().getMessage());
        assertEquals(printer, response.getBody().getData());
    }

    @Test
    void getAllPrinters_Successful() {
        List<Printer> printers = Arrays.asList(new Printer(1L, "Printer1", "Room 101", "192.168.1.1", "12345apikey"));
        when(printerRepository.findAll()).thenReturn(printers);

        ResponseEntity<ApiResponse> response = printerService.getAllPrinters();

        assertTrue(response.getBody().isSuccess());
        assertEquals("Printers retrieved successfully", response.getBody().getMessage());
        assertEquals(printers, response.getBody().getData());
    }

    @Test
    void getPrinterById_Found() {
        Printer printer = new Printer(1L, "Printer1", "Room 101", "192.168.1.1", "12345apikey");
        when(printerRepository.findById(1L)).thenReturn(Optional.of(printer));

        ResponseEntity<ApiResponse> response = printerService.getPrinterById(1L);

        assertTrue(response.getBody().isSuccess());
        assertEquals("Printer found.", response.getBody().getMessage());
        assertEquals(printer, response.getBody().getData());
    }

    @Test
    void getPrinterById_NotFound() {
        when(printerRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = printerService.getPrinterById(1L);

        assertFalse(response.getBody().isSuccess());
        assertEquals("Printer not found for ID: 1", response.getBody().getMessage());
    }

    @Test
    void updatePrinter_Successful() {
        Printer existingPrinter = new Printer(1L, "Printer1", "Room 101", "192.168.1.1", "12345apikey");
        Printer updatedDetails = new Printer(1L, "Updated Printer", "Room 102", "192.168.1.2", "67890apikey");
        when(printerRepository.findById(1L)).thenReturn(Optional.of(existingPrinter));
        when(printerRepository.save(any(Printer.class))).thenReturn(updatedDetails);

        ResponseEntity<ApiResponse> response = printerService.updatePrinter(1L, updatedDetails);

        assertTrue(response.getBody().isSuccess());
        assertEquals("Printer updated successfully.", response.getBody().getMessage());
        assertEquals(updatedDetails, response.getBody().getData());
    }

    @Test
    void updatePrinter_NotFound() {
        when(printerRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = printerService.updatePrinter(1L, new Printer());

        assertFalse(response.getBody().isSuccess());
        assertEquals("Printer not found for ID: 1", response.getBody().getMessage());
    }

    @Test
    void deletePrinter_Successful() {
        Printer printer = new Printer(1L, "Printer1", "Room 101", "192.168.1.1", "12345apikey");
        when(printerRepository.findById(1L)).thenReturn(Optional.of(printer));
        doNothing().when(printerRepository).deleteById(1L);

        ResponseEntity<ApiResponse> response = printerService.deletePrinter(1L);

        assertTrue(response.getBody().isSuccess());
        assertEquals("Printer deleted successfully.", response.getBody().getMessage());
    }

    @Test
    void deletePrinter_NotFound() {
        when(printerRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = printerService.deletePrinter(1L);

        assertFalse(response.getBody().isSuccess());
        assertEquals("Printer not found for ID: 1", response.getBody().getMessage());
    }

    @Test
    void getApiKeyById_Found() {
        Printer printer = new Printer(1L, "Printer1", "Room 101", "192.168.1.1", "12345apikey");
        when(printerRepository.findById(1L)).thenReturn(Optional.of(printer));

        ResponseEntity<ApiResponse> response = printerService.getApiKeyById(1L);

        assertTrue(response.getBody().isSuccess());
        assertEquals("API Key retrieved successfully.", response.getBody().getMessage());
        assertEquals("12345apikey", response.getBody().getData());
    }

    @Test
    void getApiKeyById_NotFound() {
        when(printerRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = printerService.getApiKeyById(1L);

        assertFalse(response.getBody().isSuccess());
        assertEquals("Printer not found for ID: 1", response.getBody().getMessage());
    }

    @Test
    void getApiKeyById_NotAvailable() {
        Printer printer = new Printer(1L, "Printer1", "Room 101", "192.168.1.1", null);
        when(printerRepository.findById(1L)).thenReturn(Optional.of(printer));

        ResponseEntity<ApiResponse> response = printerService.getApiKeyById(1L);

        assertFalse(response.getBody().isSuccess());
        assertEquals("API Key not available for the requested printer.", response.getBody().getMessage());
    }
}
