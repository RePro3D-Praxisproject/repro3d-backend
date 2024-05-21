package org.repro3d.controller;

import org.repro3d.model.RedeemCode;
import org.repro3d.service.RedeemCodeService;
import org.repro3d.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RedeemCodeController is a REST controller for managing redeem codes.
 * It provides endpoints to create, retrieve, update, validate, and delete redeem codes.
 */
@RestController
@RequestMapping("/api/redeem-code")
public class RedeemCodeController {

    private final RedeemCodeService redeemCodeService;

    /**
     * Constructor for RedeemCodeController.
     *
     * @param redeemCodeService The service to manage redeem codes.
     */
    @Autowired
    public RedeemCodeController(RedeemCodeService redeemCodeService) {
        this.redeemCodeService = redeemCodeService;
    }

    /**
     * Creates a new redeem code.
     *
     * @param redeemCode The RedeemCode object to create.
     * @return A ResponseEntity containing an ApiResponse with the result of the operation.
     */
    @PostMapping
    public ResponseEntity<ApiResponse> createRedeemCode(@RequestBody RedeemCode redeemCode) {
        return redeemCodeService.createRedeemCode(redeemCode);
    }

    /**
     * Retrieves a redeem code by its ID.
     *
     * @param id The ID of the redeem code to retrieve.
     * @return A ResponseEntity containing an ApiResponse with the result of the operation.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getRedeemCodeById(@PathVariable Long id) {
        return redeemCodeService.getRedeemCodeById(id);
    }

    /**
     * Generates a new redeem code.
     *
     * @return A ResponseEntity containing an ApiResponse with the generated redeem code.
     */
    @GetMapping("/generate")
    public ResponseEntity<ApiResponse> generateRedeemCode() {
        return redeemCodeService.generateRedeemCode();
    }

    /**
     * Validates a redeem code.
     *
     * @param code The redeem code to validate.
     * @return A ResponseEntity containing an ApiResponse with the result of the validation.
     */
    @GetMapping("/validate")
    public ResponseEntity<ApiResponse> validateRedeemCode(@RequestHeader("RC-Code") String code) {
        return redeemCodeService.validateRedeemCode(code);
    }

    /**
     * Retrieves all redeem codes.
     *
     * @return A ResponseEntity containing an ApiResponse with the list of all redeem codes.
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getAllRedeemCodes() {
        return redeemCodeService.getAllRedeemCodes();
    }

    /**
     * Updates a redeem code.
     *
     * @param id The ID of the redeem code to update.
     * @param redeemCodeDetails The details to update the redeem code with.
     * @return A ResponseEntity containing an ApiResponse with the result of the operation.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateRedeemCode(@PathVariable Long id, @RequestBody RedeemCode redeemCodeDetails) {
        return redeemCodeService.updateRedeemCode(id, redeemCodeDetails);
    }

    /**
     * Deletes a redeem code by its ID.
     *
     * @param id The ID of the redeem code to delete.
     * @return A ResponseEntity containing an ApiResponse with the result of the operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteRedeemCode(@PathVariable Long id) {
        return redeemCodeService.deleteRedeemCode(id);
    }
}
