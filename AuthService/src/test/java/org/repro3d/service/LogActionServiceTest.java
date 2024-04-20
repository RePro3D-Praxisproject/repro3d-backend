package org.repro3d.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import repro3d.model.LogAction;
import repro3d.repository.LogActionRepository;
import repro3d.service.LogActionService;
import repro3d.utils.ApiResponse;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LogActionServiceTest {

    @Mock
    private LogActionRepository logActionRepository;

    @InjectMocks
    private LogActionService logActionService;

    private LogAction logAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        logAction = new LogAction();
        logAction.setLaId(1L);
        logAction.setAction("User Login");
    }

    @Test
    void createLogAction_Success() {
        when(logActionRepository.save(any(LogAction.class))).thenReturn(logAction);
        ResponseEntity<ApiResponse> response = logActionService.createLogAction(logAction);
        assertTrue(response.getBody().isSuccess());
        assertEquals("Log action created successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void getLogActionById_Found() {
        when(logActionRepository.findById(1L)).thenReturn(Optional.of(logAction));
        ResponseEntity<ApiResponse> response = logActionService.getLogActionById(1L);
        assertTrue(response.getBody().isSuccess());
        assertEquals("Log action found", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void getLogActionById_NotFound() {
        when(logActionRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<ApiResponse> response = logActionService.getLogActionById(1L);
        assertFalse(response.getBody().isSuccess());
        assertEquals("Log action not found for ID: 1", response.getBody().getMessage());
    }

    @Test
    void getAllLogActions_Success() {
        when(logActionRepository.findAll()).thenReturn(List.of(logAction));
        ResponseEntity<ApiResponse> response = logActionService.getAllLogActions();
        assertTrue(response.getBody().isSuccess());
        assertEquals("Log actions retrieved successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void updateLogAction_Success() {
        when(logActionRepository.findById(1L)).thenReturn(Optional.of(logAction));
        when(logActionRepository.save(any(LogAction.class))).thenReturn(logAction);

        ResponseEntity<ApiResponse> response = logActionService.updateLogAction(1L, logAction);
        assertTrue(response.getBody().isSuccess());
        assertEquals("Log action updated successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void updateLogAction_NotFound() {
        when(logActionRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = logActionService.updateLogAction(1L, new LogAction());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Log action not found for ID: 1", response.getBody().getMessage());
    }

    @Test
    void deleteLogAction_Success() {
        when(logActionRepository.existsById(1L)).thenReturn(true);
        doNothing().when(logActionRepository).deleteById(1L);

        ResponseEntity<ApiResponse> response = logActionService.deleteLogAction(1L);
        assertTrue(response.getBody().isSuccess());
        assertEquals("Log action deleted successfully", response.getBody().getMessage());
    }

    @Test
    void deleteLogAction_NotFound() {
        when(logActionRepository.existsById(1L)).thenReturn(false);

        ResponseEntity<ApiResponse> response = logActionService.deleteLogAction(1L);
        assertFalse(response.getBody().isSuccess());
        assertEquals("Log action not found for ID: 1", response.getBody().getMessage());
    }
}
