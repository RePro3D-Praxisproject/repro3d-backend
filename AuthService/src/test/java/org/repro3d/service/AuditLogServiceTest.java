package org.repro3d.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import repro3d.model.AuditLog;
import repro3d.model.LogAction;
import repro3d.model.User;
import repro3d.repository.AuditLogRepository;
import repro3d.repository.LogActionRepository;
import repro3d.repository.UserRepository;
import repro3d.service.AuditLogService;
import repro3d.utils.ApiResponse;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AuditLogServiceTest {

    @Mock
    private AuditLogRepository auditLogRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LogActionRepository logActionRepository;

    @InjectMocks
    private AuditLogService auditLogService;

    private AuditLog auditLog;
    private User user;
    private LogAction logAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserId(1L);

        logAction = new LogAction();
        logAction.setLaId(1L);

        auditLog = new AuditLog();
        auditLog.setAuditId(1L);
        auditLog.setUser(user);
        auditLog.setAction(logAction);
    }

    @Test
    void createAuditLog_Success() {
        when(auditLogRepository.save(any(AuditLog.class))).thenReturn(auditLog);
        ResponseEntity<ApiResponse> response = auditLogService.createAuditLog(auditLog);
        assertTrue(response.getBody().isSuccess());
        assertEquals("Audit log created successfully.", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void getAuditLogById_Found() {
        when(auditLogRepository.findById(1L)).thenReturn(Optional.of(auditLog));
        ResponseEntity<ApiResponse> response = auditLogService.getAuditLogById(1L);
        assertTrue(response.getBody().isSuccess());
        assertEquals("Audit log found.", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void getAuditLogById_NotFound() {
        when(auditLogRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<ApiResponse> response = auditLogService.getAuditLogById(1L);
        assertFalse(response.getBody().isSuccess());
        assertEquals("Audit log not found for ID: 1", response.getBody().getMessage());
    }

    @Test
    void getAllAuditLogs_Success() {
        when(auditLogRepository.findAll()).thenReturn(List.of(auditLog));
        ResponseEntity<ApiResponse> response = auditLogService.getAllAuditLogs();
        assertTrue(response.getBody().isSuccess());
        assertEquals("Audit logs retrieved successfully.", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void updateAuditLog_Success() {
        when(auditLogRepository.findById(1L)).thenReturn(Optional.of(auditLog));
        when(userRepository.existsById(any())).thenReturn(true);
        when(logActionRepository.existsById(any())).thenReturn(true);
        when(auditLogRepository.save(any(AuditLog.class))).thenReturn(auditLog);

        ResponseEntity<ApiResponse> response = auditLogService.updateAuditLog(1L, auditLog);
        assertTrue(response.getBody().isSuccess());
        assertEquals("Audit log updated successfully.", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void updateAuditLog_NotFound() {
        when(auditLogRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = auditLogService.updateAuditLog(1L, new AuditLog());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Audit log not found for ID: 1", response.getBody().getMessage());
    }

    @Test
    void deleteAuditLog_Success() {
        when(auditLogRepository.existsById(1L)).thenReturn(true);
        doNothing().when(auditLogRepository).deleteById(1L);

        ResponseEntity<ApiResponse> response = auditLogService.deleteAuditLog(1L);
        assertTrue(response.getBody().isSuccess());
        assertEquals("Audit log deleted successfully.", response.getBody().getMessage());
    }

    @Test
    void deleteAuditLog_NotFound() {
        when(auditLogRepository.existsById(1L)).thenReturn(false);

        ResponseEntity<ApiResponse> response = auditLogService.deleteAuditLog(1L);
        assertFalse(response.getBody().isSuccess());
        assertEquals("Audit log not found for ID: 1", response.getBody().getMessage());
    }
}
