package org.repro3d.service;

import org.repro3d.model.AuditLog;
import org.repro3d.repository.AuditLogRepository;
import org.repro3d.repository.LogActionRepository;
import org.repro3d.repository.UserRepository;
import org.repro3d.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing audit logs.
 * Handles operations such as creation, retrieval, updating, and deletion of audit logs.
 */
@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    private final UserRepository userRepository;

    private final LogActionRepository logActionRepository;



    /**
     * Constructs the AuditLogService with necessary repository.
     *
     * @param auditLogRepository  The repository used for operations on audit logs.
     * @param userRepository the repository used for operations on users.
     * @param logActionRepository the repository used for operations on log actions.
     */
    @Autowired
    public AuditLogService(AuditLogRepository auditLogRepository, UserRepository userRepository, LogActionRepository logActionRepository) {
        this.auditLogRepository = auditLogRepository;
        this.userRepository = userRepository;
        this.logActionRepository = logActionRepository;
    }

    /**
     * Creates and saves a new audit log in the repository.
     *
     * @param auditLog The audit log to be created.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the result of the create operation.
     */
    public ResponseEntity<ApiResponse> createAuditLog(AuditLog auditLog) {
        AuditLog savedAuditLog = auditLogRepository.save(auditLog);
        return ResponseEntity.ok(new ApiResponse(true, "Audit log created successfully.", savedAuditLog));
    }

    /**
     * Retrieves an audit log by its ID.
     *
     * @param id The ID of the audit log to retrieve.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the audit log if found or an error message otherwise.
     */
    public ResponseEntity<ApiResponse> getAuditLogById(Long id) {
        Optional<AuditLog> auditLog = auditLogRepository.findById(id);
        return auditLog.map(log -> ResponseEntity.ok(new ApiResponse(true, "Audit log found.", log)))
                .orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Audit log not found for ID: " + id, null)));
    }

    /**
     * Retrieves all audit logs.
     *
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the list of all audit logs.
     */
    public ResponseEntity<ApiResponse> getAllAuditLogs() {
        List<AuditLog> auditLogs = auditLogRepository.findAll();
        return ResponseEntity.ok(new ApiResponse(true, "Audit logs retrieved successfully.", auditLogs));
    }

    /**
     * Updates an existing audit log identified by its ID.
     *
     * @param id              The ID of the audit log to update.
     * @param auditLogDetails The details to update the audit log with.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the updated audit log, or an error message if not found.
     */
    public ResponseEntity<ApiResponse> updateAuditLog(Long id, AuditLog auditLogDetails) {
        return auditLogRepository.findById(id)
                .map(auditLog -> {
                    if (userRepository.existsById(auditLogDetails.getUser().getUserId()) && logActionRepository.existsById(auditLogDetails.getAction().getLaId())) {
                        auditLog.setUser(auditLogDetails.getUser());
                        auditLog.setAction(auditLogDetails.getAction());
                        auditLog.setTimestamp(auditLogDetails.getTimestamp());
                        auditLogRepository.save(auditLog);
                        return ResponseEntity.ok(new ApiResponse(true, "Audit log updated successfully.", auditLog));
                    } else {
                        return ResponseEntity.badRequest().body(new ApiResponse(false, "Referenced User or LogAction does not exist.", null));
                    }
                })
                .orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Audit log not found for ID: " + id, null)));
    }



    /**
     * Deletes an audit log from the repository identified by its ID.
     *
     * @param id The ID of the audit log to delete.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating the result of the delete operation.
     */
    public ResponseEntity<ApiResponse> deleteAuditLog(Long id) {
        if (auditLogRepository.existsById(id)) {
            auditLogRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse(true, "Audit log deleted successfully.", null));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "Audit log not found for ID: " + id, null));
        }
    }
}
