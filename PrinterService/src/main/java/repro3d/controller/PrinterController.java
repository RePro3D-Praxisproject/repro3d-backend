package repro3d.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repro3d.model.Printer;
import repro3d.service.PrinterService;
import repro3d.utils.ApiResponse;

import java.util.List;

/**
 * Controller for managing {@code Printer} entities.
 * <p>
 * This class provides RESTful web APIs to perform CRUD operations on printers,
 * including creating new printers, retrieving details of a specific printer,
 * updating printer information, and deleting printers.
 */
@RestController
@RequestMapping("/printers")
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
}
