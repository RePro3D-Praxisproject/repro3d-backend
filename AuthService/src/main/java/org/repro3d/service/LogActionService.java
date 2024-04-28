package org.repro3d.service;

import org.repro3d.model.LogAction;
import org.repro3d.repository.LogActionRepository;
import org.repro3d.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for handling operations related to {@link LogAction} entities.
 * This service encapsulates the business logic for creating, retrieving, updating, and deleting log actions,
 * interacting with the {@link LogActionRepository} to perform these operations.
 */
@Service
public class LogActionService {

    private final LogActionRepository logActionRepository;

    /**
     * Constructs a {@code LogActionService} with the necessary {@link LogActionRepository}.
     *
     * @param logActionRepository The repository used for data operations on log actions.
     */
    @Autowired
    public LogActionService(LogActionRepository logActionRepository) {
        this.logActionRepository = logActionRepository;
    }

    /**
     * Creates and saves a new log action in the repository.
     *
     * @param logAction The log action to be created and saved.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the result of the create operation.
     */
    public ResponseEntity<ApiResponse> createLogAction(LogAction logAction) {
        LogAction savedLogAction = logActionRepository.save(logAction);
        return ResponseEntity.ok(new ApiResponse(true, "Log action created successfully", savedLogAction));
    }

    /**
     * Retrieves a log action by its ID.
     *
     * @param id The ID of the log action to retrieve.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the log action if found, or an error message otherwise.
     */
    public ResponseEntity<ApiResponse> getLogActionById(Long id) {
        Optional<LogAction> logAction = logActionRepository.findById(id);
        if (logAction.isPresent()) {
            return ResponseEntity.ok(new ApiResponse(true, "Log action found", logAction.get()));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "Log action not found for ID: " + id, null));
        }
    }

    /**
     * Retrieves all log actions in the repository.
     *
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the list of all log actions.
     */
    public ResponseEntity<ApiResponse> getAllLogActions() {
        Iterable<LogAction> logActions = logActionRepository.findAll();
        return ResponseEntity.ok(new ApiResponse(true, "Log actions retrieved successfully", logActions));
    }

    /**
     * Updates an existing log action identified by its ID with new details.
     *
     * @param id          The ID of the log action to update.
     * @param logActionDetails The new details for the log action.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the updated log action, or an error message if the log action was not found.
     */
    public ResponseEntity<ApiResponse> updateLogAction(Long id, LogAction logActionDetails) {
        return logActionRepository.findById(id)
                .map(logAction -> {
                    logAction.setAction(logActionDetails.getAction());
                    logActionRepository.save(logAction);
                    return ResponseEntity.ok(new ApiResponse(true, "Log action updated successfully", logAction));
                }).orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Log action not found for ID: " + id, null)));
    }

    /**
     * Deletes a log action from the repository identified by its ID.
     *
     * @param id The ID of the log action to delete.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating the result of the delete operation.
     */
    public ResponseEntity<ApiResponse> deleteLogAction(Long id) {
        if (logActionRepository.existsById(id)) {
            logActionRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse(true, "Log action deleted successfully", null));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "Log action not found for ID: " + id, null));
        }
    }
}
