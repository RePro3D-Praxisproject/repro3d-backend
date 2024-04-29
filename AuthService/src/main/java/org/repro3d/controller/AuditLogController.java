package org.repro3d.controller;

import org.repro3d.model.AuditLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.repro3d.service.AuditLogService;
import org.repro3d.utils.ApiResponse;

/**
 * Controller for managing {@code AuditLog} entities.
 * Provides RESTful APIs for CRUD operations on audit logs, including creating, retrieving,
 * updating, and deleting audit logs.
 */
@RestController
@RequestMapping("/api/audit-log")
public class AuditLogController {

    private final AuditLogService auditLogService;

    /**
     * Constructs an {@code AuditLogController} with the necessary {@link AuditLogService}.
     * @param auditLogService The service used to perform operations on audit logs.
     */
    @Autowired
    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    /**
     * Creates a new audit log.
     * @param auditLog The audit log to create.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
     *         the result of the operation.
     */
    @PostMapping
    public ResponseEntity<ApiResponse> createAuditLog(@RequestBody AuditLog auditLog) {
        return auditLogService.createAuditLog(auditLog);
    }

    /**
     * Retrieves an audit log by its ID.
     * @param id The ID of the audit log to retrieve.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the audit log
     *         if found or an error message otherwise.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getAuditLogById(@PathVariable Long id) {
        return auditLogService.getAuditLogById(id);
    }

    /**
     * Retrieves all audit logs.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with a list
     *         of all audit logs.
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getAllAuditLogs() {
        return auditLogService.getAllAuditLogs();
    }

    /**
     * Updates the details of an existing audit log.
     * @param id The ID of the audit log to update.
     * @param auditLogDetails The new details for the audit log.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
     *         the result of the update operation.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateAuditLog(@PathVariable Long id, @RequestBody AuditLog auditLogDetails) {
        return auditLogService.updateAuditLog(id, auditLogDetails);
    }

    /**
     * Deletes an audit log by its ID.
     * @param id The ID of the audit log to delete.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
     *         the result of the delete operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAuditLog(@PathVariable Long id) {
        return auditLogService.deleteAuditLog(id);
    }
}
