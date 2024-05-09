package org.repro3d.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.repro3d.service.PrinterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.repro3d.model.Printer;
import org.repro3d.utils.ApiResponse;
/**
 * Controller for managing {@code Printer} entities.
 * <p>
 * This class provides RESTful web APIs to perform CRUD operations on printers,
 * including creating new printers, retrieving details of a specific printer,
 * updating printer information, and deleting printers.
 */
@RestController
@RequestMapping("/api/printer")
public class PrinterController {

    private final PrinterService printerService;

    /**
     * Constructs a {@code PrinterController} with a dependency on {@link PrinterService}.
     * This service is injected at runtime by Spring Framework's dependency injection facilities.
     *
     * @param printerService The service used for operations on {@link Printer} entities.
     */
    @Autowired
    public PrinterController(PrinterService printerService) {
        this.printerService = printerService;
    }

    /**
     * Creates a new printer and saves it to the database.
     *
     * @param printer The {@link Printer} entity to be created.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} that includes
     *         the result of the create operation, along with the created printer entity.
     */
    @PostMapping
    public ResponseEntity<ApiResponse> createPrinter(@RequestBody Printer printer) {
        return printerService.createPrinter(printer);
    }

    /**
     * Retrieves all printers.
     *
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with a list
     *         of all printers.
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getAllPrinters() {
        return printerService.getAllPrinters();
    }

    /**
     * Retrieves the details of a printer identified by its ID.
     *
     * @param id The ID of the printer to retrieve.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} that includes
     *         the details of the requested printer, or an error message if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getPrinterById(@PathVariable Long id) {
        return printerService.getPrinterById(id);
    }

    /**
     * Updates the information of an existing printer identified by its ID.
     *
     * @param id The ID of the printer to update.
     * @param printer The new information for the printer.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} that indicates
     *         the result of the update operation.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updatePrinter(@PathVariable Long id, @RequestBody Printer printer) {
        return printerService.updatePrinter(id, printer);
    }

    /**
     * Retrieves the API key of a printer identified by its ID.
     * This endpoint should be secured to ensure that only authorized users can access the API key.
     *
     * @param id The ID of the printer from which to retrieve the API key.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the API key or an error message.
     */
    @GetMapping("/{id}/apikey")
    public ResponseEntity<ApiResponse> getPrinterApiKeyById(@PathVariable Long id) {
        return printerService.getApiKeyById(id);
    }

    /**
     * Deletes a printer identified by its ID from the database.
     *
     * @param id The ID of the printer to delete.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} that indicates
     *         the result of the delete operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletePrinter(@PathVariable Long id) {
        return printerService.deletePrinter(id);
    }

    /**
     * Streams live webcam footage for a specific printer identified by its ID. This method
     * interfaces with the {@link PrinterService} to initiate streaming based on the printer's
     * network IP address and API key. It assumes that the printer is equipped with a webcam
     * and the necessary infrastructure to support live streaming.
     *
     * @param id The ID of the printer for which webcam footage is to be streamed.
     * @param response The {@link HttpServletResponse} that streams the live footage.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse}. If the streaming is
     *         initiated successfully, it returns a success message. If the printer is not found,
     *         or if the IP address/API key are not configured, it returns an appropriate error message.
     */
    @GetMapping("/webcam/{id}")
    public ResponseEntity<ApiResponse> stream(@PathVariable Long id, HttpServletResponse response) {
        return printerService.streamWebcam(id, response);
    }
}




