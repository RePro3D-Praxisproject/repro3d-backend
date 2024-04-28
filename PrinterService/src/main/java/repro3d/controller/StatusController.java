package repro3d.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repro3d.model.Status;
import repro3d.service.StatusService;
import repro3d.utils.ApiResponse;

/**
 * Controller for managing {@code Status} entities.
 * <p>
 * This controller provides RESTful APIs to perform CRUD operations on status entities,
 * allowing for the creation, retrieval, update, and deletion of statuses, as well as
 * the ability to retrieve a list of all statuses.
 */
@RestController
@RequestMapping("/api/status")
public class StatusController {

    private final StatusService statusService;

    /**
     * Constructs a {@code StatusController} with the necessary {@link StatusService}.
     *
     * @param statusService The service used to perform operations on status entities.
     */
    @Autowired
    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    /**
     * Creates a new status.
     *
     * @param status The status to create.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
     *         the result of the create operation.
     */
    @PostMapping
    public ResponseEntity<ApiResponse> createStatus(@RequestBody Status status) {
        return statusService.createStatus(status);
    }

    /**
     * Retrieves a specific status by its ID.
     *
     * @param id The ID of the status to retrieve.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the status
     *         if found, or an error message otherwise.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getStatusById(@PathVariable Long id) {
        return statusService.getStatusById(id);
    }

    /**
     * Updates an existing status.
     *
     * @param id     The ID of the status to update.
     * @param status The new details for the status.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
     *         the result of the update operation.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateStatus(@PathVariable Long id, @RequestBody Status status) {
        return statusService.updateStatus(id, status);
    }

    /**
     * Deletes a status by its ID.
     *
     * @param id The ID of the status to delete.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
     *         the result of the delete operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteStatus(@PathVariable Long id) {
        return statusService.deleteStatus(id);
    }
}
