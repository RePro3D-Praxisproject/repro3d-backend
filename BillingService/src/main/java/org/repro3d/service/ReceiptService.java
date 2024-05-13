package org.repro3d.service;

import org.repro3d.model.Order;
import org.repro3d.model.Receipt;
import org.repro3d.repository.OrderRepository;
import org.repro3d.repository.ReceiptRepository;
import org.repro3d.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing receipts within the application.
 * Handles operations such as creation, retrieval, updating, and deletion of receipts,
 * with verification against the existence of associated orders.
 */
@Service
public class ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final OrderRepository orderRepository;

    /**
     * Constructs the service with the necessary receipt and order repositories.
     *
     * @param receiptRepository The repository used for receipt operations.
     * @param orderRepository The repository used for order validations.
     */
    @Autowired
    public ReceiptService(ReceiptRepository receiptRepository, OrderRepository orderRepository) {
        this.receiptRepository = receiptRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * Creates and saves a new receipt in the repository after verifying the associated order exists.
     *
     * @param receipt The receipt to be created.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the result of the create operation.
     */
    public ResponseEntity<ApiResponse> createReceipt(Receipt receipt) {
        Optional<Order> order = orderRepository.findById(receipt.getOrder().getOrderId());
        if (!order.isPresent()) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Order does not exist.", null));
        }

        Receipt savedReceipt = receiptRepository.save(receipt);
        return ResponseEntity.ok(new ApiResponse(true, "Receipt created successfully.", savedReceipt));
    }

    /**
     * Retrieves a receipt by its ID.
     *
     * @param id The ID of the receipt to retrieve.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the receipt if found or an error message otherwise.
     */
    public ResponseEntity<ApiResponse> getReceiptById(Long id) {
        Optional<Receipt> receipt = receiptRepository.findById(id);
        return receipt.map(r -> ResponseEntity.ok(new ApiResponse(true, "Receipt found.", r)))
                .orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Receipt not found for ID: " + id, null)));
    }

    /**
     * Retrieves all receipts from the repository.
     *
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with a list of all receipts.
     */
    public ResponseEntity<ApiResponse> getAllReceipts() {
        List<Receipt> receipts = receiptRepository.findAll();
        return ResponseEntity.ok(new ApiResponse(true, "Receipts retrieved successfully.", receipts));
    }

    /**
     * Updates the details of an existing receipt after verifying the associated order exists.
     *
     * @param id The ID of the receipt to update.
     * @param receiptDetails The new details for the receipt.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating the result of the update operation.
     */
    public ResponseEntity<ApiResponse> updateReceipt(Long id, Receipt receiptDetails) {
        return receiptRepository.findById(id).flatMap(receipt -> {
            Optional<Order> order = orderRepository.findById(receiptDetails.getOrder().getOrderId());
            if (!order.isPresent()) {
                return Optional.of(ResponseEntity.badRequest().body(new ApiResponse(false, "Order does not exist.", null)));
            }

            receipt.setPaidOn(receiptDetails.getPaidOn());
            receipt.setSumTotal(receiptDetails.getSumTotal());
            receipt.setOrder(receiptDetails.getOrder());
            receiptRepository.save(receipt);
            return Optional.of(ResponseEntity.ok(new ApiResponse(true, "Receipt updated successfully.", receipt)));
        }).orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Receipt not found for ID: " + id, null)));
    }

    /**
     * Deletes an existing receipt from the repository.
     *
     * @param id The ID of the receipt to delete.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating the result of the delete operation.
     */
    public ResponseEntity<ApiResponse> deleteReceipt(Long id) {
        if (receiptRepository.existsById(id)) {
            receiptRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse(true, "Receipt deleted successfully.", null));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "Receipt not found for ID: " + id, null));
        }
    }
}
