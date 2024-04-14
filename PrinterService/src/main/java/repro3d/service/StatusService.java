package repro3d.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import repro3d.model.Status;
import repro3d.repository.StatusRepository;
import repro3d.utils.ApiResponse;

import java.util.List;
import java.util.Optional;

/**
 * Service class for handling operations related to {@link Status} entities.
 * <p>
 * This class encapsulates the business logic for creating, retrieving, updating, and deleting status entities,
 * interfacing with the {@link StatusRepository} to perform these operations. It provides a centralized access point
 * for status management within the application.
 */
@Service
public class StatusService {

    private final StatusRepository statusRepository;

    /**
     * Constructs a {@code StatusService} with a dependency on the {@link StatusRepository}.
     *
     * @param statusRepository The repository used for data operations on statuses.
     */
    @Autowired
    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    /**
     * Creates a new status in the repository.
     *
     * @param status The status object to be created.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the result of the create operation.
     */
    public ResponseEntity<ApiResponse> createStatus(Status status) {
        Status savedStatus = statusRepository.save(status);
        return ResponseEntity.ok(new ApiResponse(true, "Status created successfully.", savedStatus));
    }

    /**
     * Retrieves a specific status by its ID.
     *
     * @param id The ID of the status to retrieve.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the status if found, or an error message otherwise.
     */
    public ResponseEntity<ApiResponse> getStatusById(Long id) {
        Optional<Status> status = statusRepository.findById(id);
        if (status.isPresent()) {
            return ResponseEntity.ok(new ApiResponse(true, "Status found.", status.get()));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "Status not found for ID: " + id, null));
        }
    }

    /**
     * Retrieves all statuses from the repository.
     *
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with a list of all statuses.
     */
    public ResponseEntity<ApiResponse> getAllStatuses() {
        List<Status> statuses = statusRepository.findAll();
        return ResponseEntity.ok(new ApiResponse(true, "Statuses retrieved successfully", statuses));
    }

    /**
     * Updates an existing status identified by its ID with new details.
     *
     * @param id The ID of the status to update.
     * @param statusDetails The details to update the status with.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the updated status, or an error message if the status was not found.
     */
    public ResponseEntity<ApiResponse> updateStatus(Long id, Status statusDetails) {
        return statusRepository.findById(id)
                .map(status -> {
                    status.setStatus(statusDetails.getStatus());
                    Status updatedStatus = statusRepository.save(status);
                    return ResponseEntity.ok(new ApiResponse(true, "Status updated successfully.", updatedStatus));
                }).orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Status not found for ID: " + id, null)));
    }

    /**
     * Deletes a status from the repository based on its ID.
     *
     * @param id The ID of the status to delete.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating the result of the delete operation.
     */
    public ResponseEntity<ApiResponse> deleteStatus(Long id) {
        if (statusRepository.existsById(id)) {
            statusRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse(true, "Status deleted successfully.", null));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "Status not found for ID: " + id, null));
        }
    }
}
