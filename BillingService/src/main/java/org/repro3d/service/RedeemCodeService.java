package org.repro3d.service;

import org.repro3d.model.RedeemCode;
import org.repro3d.repository.RedeemCodeRepository;
import org.repro3d.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for managing redeem codes within the application.
 * Handles operations such as creation, generation, validation, retrieval,
 * updating, and deletion of redeem codes.
 */
@Service
public class RedeemCodeService {

    private final RedeemCodeRepository redeemCodeRepository;

    /**
     * Constructs the service with the necessary redeem code repository.
     * @param redeemCodeRepository The repository used for redeem code operations.
     */
    @Autowired
    public RedeemCodeService(RedeemCodeRepository redeemCodeRepository) {
        this.redeemCodeRepository = redeemCodeRepository;
    }

    /**
     * Creates and saves a new redeem code in the repository.
     *
     * @param redeemCode The redeem code to be created.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the result of the create operation.
     */
    public ResponseEntity<ApiResponse> createRedeemCode(RedeemCode redeemCode) {
        RedeemCode savedRedeemCode = redeemCodeRepository.save(redeemCode);
        return ResponseEntity.ok(new ApiResponse(true, "Redeem code created successfully.", savedRedeemCode));
    }

    /**
     * Generates a unique redeem code, encodes it with Base64, and saves it in the repository.
     *
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the generated redeem code.
     */
    public ResponseEntity<ApiResponse> generateRedeemCode() {
        String randomCode = UUID.randomUUID().toString();
        String encodedCode = Base64.getEncoder().encodeToString(randomCode.getBytes());
        RedeemCode redeemCode = new RedeemCode(null, encodedCode, false);
        RedeemCode savedRedeemCode = redeemCodeRepository.save(redeemCode);
        return ResponseEntity.ok(new ApiResponse(true, "Redeem code generated successfully.", savedRedeemCode));
    }

    /**
     * Validates a redeem code by checking its existence and whether it has been used.
     *
     * @param code The Base64-encoded redeem code to validate.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating whether the redeem code is valid.
     */
    public ResponseEntity<ApiResponse> validateRedeemCode(String code) {
        Optional<RedeemCode> redeemCode = redeemCodeRepository.findByRcCode(code);
        if (!redeemCode.isPresent()) {
            return ResponseEntity.ok(new ApiResponse(false, "Redeem code is invalid.", null));
        }
        boolean isValid = !redeemCode.get().getUsed();
        if (!isValid) {
            return ResponseEntity.ok(new ApiResponse(false, "Redeem code is invalid.", null));
        } else {
            return ResponseEntity.ok(new ApiResponse(true, "Redeem code is valid.", null));
        }
    }

    /**
     * Retrieves a redeem code by its ID.
     *
     * @param id The ID of the redeem code to retrieve.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the redeem code if found or an error message otherwise.
     */
    public ResponseEntity<ApiResponse> getRedeemCodeById(Long id) {
        Optional<RedeemCode> redeemCode = redeemCodeRepository.findById(id);
        return redeemCode.map(code -> ResponseEntity.ok(new ApiResponse(true, "Redeem code found.", code)))
                .orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Redeem code not found for ID: " + id, null)));
    }

    /**
     * Retrieves all redeem codes.
     *
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with a list of all redeem codes.
     */
    public ResponseEntity<ApiResponse> getAllRedeemCodes() {
        List<RedeemCode> redeemCodes = redeemCodeRepository.findAll();
        return ResponseEntity.ok(new ApiResponse(true, "Redeem codes retrieved successfully.", redeemCodes));
    }

    /**
     * Updates the details of an existing redeem code.
     *
     * @param id The ID of the redeem code to update.
     * @param redeemCodeDetails The new details for the redeem code.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating the result of the update operation.
     */
    public ResponseEntity<ApiResponse> updateRedeemCode(Long id, RedeemCode redeemCodeDetails) {
        return redeemCodeRepository.findById(id)
                .map(redeemCode -> {
                    redeemCode.setRcCode(redeemCodeDetails.getRcCode());
                    redeemCode.setUsed(redeemCodeDetails.getUsed());
                    redeemCodeRepository.save(redeemCode);
                    return ResponseEntity.ok(new ApiResponse(true, "Redeem code updated successfully.", redeemCode));
                })
                .orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Redeem code not found for ID: " + id, null)));
    }

    /**
     * Deletes an existing redeem code from the repository.
     *
     * @param id The ID of the redeem code to delete.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating the result of the delete operation.
     */
    public ResponseEntity<ApiResponse> deleteRedeemCode(Long id) {
        if (redeemCodeRepository.existsById(id)) {
            redeemCodeRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse(true, "Redeem code deleted successfully.", null));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "Redeem code not found for ID: " + id, null));
        }
    }
}
