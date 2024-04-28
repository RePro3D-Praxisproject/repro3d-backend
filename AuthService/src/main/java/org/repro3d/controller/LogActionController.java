package org.repro3d.controller;

import org.repro3d.model.LogAction;
import org.repro3d.service.LogActionService;
import org.repro3d.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing {@link LogAction} entities.
 * Provides endpoints for CRUD operations on log actions, including creation, retrieval,
 * updating, and deletion of log actions.
 */
@RestController
@RequestMapping("/api/log-action")
public class LogActionController {

    private final LogActionService logActionService;

    /**
     * Constructs a {@code LogActionController} with the necessary {@link LogActionService}.
     * @param logActionService The service used to perform operations on log actions.
     */
    @Autowired
    public LogActionController(LogActionService logActionService) {
        this.logActionService = logActionService;
    }

    /**
     * Creates a new log action.
     * @param logAction The log action to create.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
     *         the result of the operation.
     */
    @PostMapping
    public ResponseEntity<ApiResponse> createLogAction(@RequestBody LogAction logAction) {
        return logActionService.createLogAction(logAction);
    }

    /**
     * Retrieves a log action by its ID.
     * @param id The ID of the log action to retrieve.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the log action
     *         if found or an error message otherwise.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getLogActionById(@PathVariable Long id) {
        return logActionService.getLogActionById(id);
    }

    /**
     * Retrieves all log actions.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with a list
     *         of all log actions.
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getAllLogActions() {
        return logActionService.getAllLogActions();
    }

    /**
     * Updates the details of an existing log action.
     * @param id The ID of the log action to update.
     * @param logActionDetails The new details for the log action.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
     *         the result of the update operation.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateLogAction(@PathVariable Long id, @RequestBody LogAction logActionDetails) {
        return logActionService.updateLogAction(id, logActionDetails);
    }

    /**
     * Deletes a log action by its ID.
     * @param id The ID of the log action to delete.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
     *         the result of the delete operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteLogAction(@PathVariable Long id) {
        return logActionService.deleteLogAction(id);
    }
}
