package org.repro3d.controller;

import org.repro3d.model.Receipt;
import org.repro3d.service.ReceiptService;
import org.repro3d.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receipt")
public class ReceiptController {

    private final ReceiptService receiptService;

    @Autowired
    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createReceipt(@RequestBody Receipt receipt) {
        return receiptService.createReceipt(receipt);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getReceiptById(@PathVariable Integer id) {
        return receiptService.getReceiptById(id);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllReceipts() {
        return receiptService.getAllReceipts();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateReceipt(@PathVariable Integer id, @RequestBody Receipt receiptDetails) {
        return receiptService.updateReceipt(id, receiptDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteReceipt(@PathVariable Integer id) {
        return receiptService.deleteReceipt(id);
    }
}
