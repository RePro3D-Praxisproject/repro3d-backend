package org.repro3d.controller;

import org.repro3d.model.Receipt;
import org.repro3d.service.ReceiptService;
import org.repro3d.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ReceiptController is a REST controller for managing receipts.
 * It provides endpoints to create, retrieve, update, and delete receipts.
 */
@RestController
@RequestMapping("/api/receipt")
public class ReceiptController {

    private final ReceiptService receiptService;

    /**
     * Constructor for ReceiptController.
     *
     * @param receiptService The service to manage receipts.
     */
    @Autowired
    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    /**
     * Creates a new receipt.
     *
     * @param receipt The Receipt object to create.
     * @return A ResponseEntity containing an ApiResponse with the result of the operation.
     */
    @PostMapping
    public ResponseEntity<ApiResponse> createReceipt(@RequestBody Receipt receipt) {
        return receiptService.createReceipt(receipt);
    }

    /**
     * Retrieves a receipt by its ID.
     *
     * @param id The ID of the receipt to retrieve.
     * @return A ResponseEntity containing an ApiResponse with the result of the operation.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getReceiptById(@PathVariable Long id) {
        return receiptService.getReceiptById(id);
    }

    /**
     * Retrieves all receipts.
     *
     * @return A ResponseEntity containing an ApiResponse with the list of all receipts.
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getAllReceipts() {
        return receiptService.getAllReceipts();
    }

    /**
     * Updates a receipt.
     *
     * @param id The ID of the receipt to update.
     * @param receiptDetails The details to update the receipt with.
     * @return A ResponseEntity containing an ApiResponse with the result of the operation.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateReceipt(@PathVariable Long id, @RequestBody Receipt receiptDetails) {
        return receiptService.updateReceipt(id, receiptDetails);
    }

    /**
     * Deletes a receipt by its ID.
     *
     * @param id The ID of the receipt to delete.
     * @return A ResponseEntity containing an ApiResponse with the result of the operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteReceipt(@PathVariable Long id) {
        return receiptService.deleteReceipt(id);
    }
}
