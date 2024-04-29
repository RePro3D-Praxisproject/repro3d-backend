package org.repro3d.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.repro3d.model.Status;
import org.repro3d.repository.StatusRepository;
import org.springframework.http.ResponseEntity;
import org.repro3d.utils.ApiResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class StatusServiceTest {

    @Mock
    private StatusRepository statusRepository;

    @InjectMocks
    private StatusService statusService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createStatus_Successful() {
        Status status = new Status(1L, "Active");
        when(statusRepository.save(any(Status.class))).thenReturn(status);

        ResponseEntity<ApiResponse> response = statusService.createStatus(status);

        assertTrue(response.getBody().isSuccess());
        assertEquals("Status created successfully.", response.getBody().getMessage());
        assertEquals(status, response.getBody().getData());
    }

    @Test
    void getStatusById_Found() {
        Status status = new Status(1L, "Active");
        when(statusRepository.findById(1L)).thenReturn(Optional.of(status));

        ResponseEntity<ApiResponse> response = statusService.getStatusById(1L);

        assertTrue(response.getBody().isSuccess());
        assertEquals("Status found.", response.getBody().getMessage());
        assertEquals(status, response.getBody().getData());
    }

    @Test
    void getStatusById_NotFound() {
        when(statusRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = statusService.getStatusById(1L);

        assertFalse(response.getBody().isSuccess());
        assertEquals("Status not found for ID: 1", response.getBody().getMessage());
    }

    @Test
    void getAllStatuses_Successful() {
        List<Status> statuses = Arrays.asList(new Status(1L, "Active"), new Status(2L, "Inactive"));
        when(statusRepository.findAll()).thenReturn(statuses);

        ResponseEntity<ApiResponse> response = statusService.getAllStatuses();

        assertTrue(response.getBody().isSuccess());
        assertEquals("Statuses retrieved successfully", response.getBody().getMessage());
        assertEquals(statuses, response.getBody().getData());
    }

    @Test
    void updateStatus_Successful() {
        Status existingStatus = new Status(1L, "Active");
        Status updatedDetails = new Status(1L, "Inactive");
        when(statusRepository.findById(1L)).thenReturn(Optional.of(existingStatus));
        when(statusRepository.save(any(Status.class))).thenReturn(updatedDetails);

        ResponseEntity<ApiResponse> response = statusService.updateStatus(1L, updatedDetails);

        assertTrue(response.getBody().isSuccess());
        assertEquals("Status updated successfully.", response.getBody().getMessage());
        assertEquals(updatedDetails, response.getBody().getData());
    }

    @Test
    void updateStatus_NotFound() {
        when(statusRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = statusService.updateStatus(1L, new Status());

        assertFalse(response.getBody().isSuccess());
        assertEquals("Status not found for ID: 1", response.getBody().getMessage());
    }

    @Test
    void deleteStatus_Successful() {
        when(statusRepository.existsById(1L)).thenReturn(true);
        doNothing().when(statusRepository).deleteById(1L);

        ResponseEntity<ApiResponse> response = statusService.deleteStatus(1L);

        assertTrue(response.getBody().isSuccess());
        assertEquals("Status deleted successfully.", response.getBody().getMessage());
    }

    @Test
    void deleteStatus_NotFound() {
        when(statusRepository.existsById(anyLong())).thenReturn(false);

        ResponseEntity<ApiResponse> response = statusService.deleteStatus(1L);

        assertFalse(response.getBody().isSuccess());
        assertEquals("Status not found for ID: 1", response.getBody().getMessage());
    }
}
