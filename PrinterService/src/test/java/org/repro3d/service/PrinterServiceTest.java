package org.repro3d.service;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.repro3d.service.PrinterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.BeforeEach;
import org.repro3d.model.Printer;
import org.repro3d.repository.PrinterRepository;
import org.repro3d.utils.ApiResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PrinterServiceTest {

    @Mock
    private PrinterService printerService;

    @Mock
    private PrinterRepository printerRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    public void testCreatePrinter() {
        Printer printer = new Printer();
        printer.setName("Test Printer");
        when(printerRepository.save(any(Printer.class))).thenReturn(printer);

        ResponseEntity<ApiResponse> response = printerService.createPrinter(printer);
        assertEquals("Printer created successfully.", response.getBody().getMessage());
        assertEquals(true, response.getBody().isSuccess());
        assertEquals(printer, response.getBody().getData());
    }

    @Test
    public void testGetAllPrinters() {
        Printer printer1 = new Printer();
        Printer printer2 = new Printer();
        when(printerRepository.findAll()).thenReturn(Arrays.asList(printer1, printer2));

        ResponseEntity<ApiResponse> response = printerService.getAllPrinters();
        assertEquals("Printers retrieved successfully", response.getBody().getMessage());
        assertEquals(true, response.getBody().isSuccess());
        assertEquals(2, ((List<Printer>) response.getBody().getData()).size());
    }

    @Test
    public void testGetPrinterById() {
        Printer printer = new Printer();
        printer.setPrinter_id(1L);
        when(printerRepository.findById(1L)).thenReturn(Optional.of(printer));

        ResponseEntity<ApiResponse> response = printerService.getPrinterById(1L);
        assertEquals("Printer found.", response.getBody().getMessage());
        assertEquals(true, response.getBody().isSuccess());
        assertEquals(printer, response.getBody().getData());
    }

    @Test
    public void testUpdatePrinter() {
        Printer existingPrinter = new Printer();
        existingPrinter.setPrinter_id(1L);
        existingPrinter.setName("Old Printer");

        Printer newDetails = new Printer();
        newDetails.setName("Updated Printer");

        when(printerRepository.findById(1L)).thenReturn(Optional.of(existingPrinter));
        when(printerRepository.save(any(Printer.class))).thenReturn(existingPrinter);

        ResponseEntity<ApiResponse> response = printerService.updatePrinter(1L, newDetails);
        assertEquals("Printer updated successfully.", response.getBody().getMessage());
        assertEquals(true, response.getBody().isSuccess());
        verify(printerRepository).save(existingPrinter);
    }

    @Test
    public void testDeletePrinter() {
        when(printerRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<ApiResponse> response = printerService.deletePrinter(1L);
        assertEquals("Printer deleted successfully.", response.getBody().getMessage());
        assertEquals(true, response.getBody().isSuccess());
        verify(printerRepository, times(1)).deleteById(1L);
    }
}
