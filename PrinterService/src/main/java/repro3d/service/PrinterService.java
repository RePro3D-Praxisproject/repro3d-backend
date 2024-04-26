package repro3d.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import repro3d.model.Printer;
import repro3d.repository.PrinterRepository;
import repro3d.utils.ApiResponse;

import java.util.List;

/**
 * Service class for handling operations related to {@link Printer} entities.
 * <p>
 * Encapsulates the business logic for creating, retrieving, updating, and deleting printers,
 * interacting with the {@link PrinterRepository} to perform these operations.
 */
@Service
public class PrinterService {

    private final PrinterRepository printerRepository;

    /**
     * Constructs a {@code PrinterService} with the necessary {@link PrinterRepository}.
     *
     * @param printerRepository The repository used for data operations on printers.
     */
    @Autowired
    public PrinterService(PrinterRepository printerRepository) {
        this.printerRepository = printerRepository;
    }

    /**
     * Creates and saves a new printer in the repository.
     *
     * @param printer The printer to be created and saved.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the result of the create operation.
     */
    public ResponseEntity<ApiResponse> createPrinter(Printer printer) {
        Printer savedPrinter = printerRepository.save(printer);
        return ResponseEntity.ok(new ApiResponse(true, "Printer created successfully.", savedPrinter));
    }

    /**
     * Retrieves all printers.
     *
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with a list
     *         of all printers.
     */
    public ResponseEntity<ApiResponse> getAllPrinters() {
        List<Printer> printers = printerRepository.findAll();
        return ResponseEntity.ok(new ApiResponse(true, "Printers retrieved successfully", printers));
    }

    /**
     * Retrieves a printer by its ID.
     *
     * @param id The ID of the printer to retrieve.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the printer if found, or an error message otherwise.
     */
    public ResponseEntity<ApiResponse> getPrinterById(Long id) {
        return printerRepository.findById(id)
                .map(printer -> ResponseEntity.ok(new ApiResponse(true, "Printer found.", printer)))
                .orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Printer not found for ID: " + id, null)));
    }

    /**
     * Updates an existing printer identified by its ID with new details.
     *
     * @param id The ID of the printer to update.
     * @param printerDetails The new details for the printer.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the updated printer, or an error message if the printer was not found.
     */
    public ResponseEntity<ApiResponse> updatePrinter(Long id, Printer printerDetails) {
        return printerRepository.findById(id)
                .map(printer -> {
                    printer.setName(printerDetails.getName());
                    printer.setLocation(printerDetails.getLocation());
                    printer.setIp_addr(printerDetails.getIp_addr());
                    printer.setApikey(printerDetails.getApikey());
                    Printer updatedPrinter = printerRepository.save(printer);
                    return ResponseEntity.ok(new ApiResponse(true, "Printer updated successfully.", updatedPrinter));
                }).orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Printer not found for ID: " + id, null)));
    }

    /**
     * Deletes a printer from the repository identified by its ID.
     *
     * @param id The ID of the printer to delete.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating the result of the delete operation.
     */
    public ResponseEntity<ApiResponse> deletePrinter(Long id) {
        return printerRepository.findById(id)
                .map(printer -> {
                    printerRepository.deleteById(id);
                    return ResponseEntity.ok(new ApiResponse(true, "Printer deleted successfully.", null));
                }).orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Printer not found for ID: " + id, null)));
    }

    /**
     * Retrieves the API key for a specific printer identified by its ID.
     *
     * @param id The ID of the printer whose API key is to be retrieved.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse}. The ApiResponse will include a success status
     *         and the API key if the printer is found and the API key is available, or an error message otherwise.
     */
    public ResponseEntity<ApiResponse> getApiKeyById(Long id){
        return printerRepository.findById(id)
                .map(printer -> {
                    String apiKey = printer.getApikey();
                    if (apiKey == null || apiKey.isEmpty()) {
                        return ResponseEntity.ok(new ApiResponse(false, "API Key not available for the requested printer.", null));
                    }
                    return ResponseEntity.ok(new ApiResponse(true, "API Key retrieved successfully.", apiKey));
                }).orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Printer not found for ID: " + id, null)));
    }
}
