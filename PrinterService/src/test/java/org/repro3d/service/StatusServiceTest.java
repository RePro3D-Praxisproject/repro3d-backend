package repro3d.service;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import repro3d.model.Status;
import repro3d.repository.StatusRepository;
import repro3d.utils.ApiResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StatusServiceTest {

    @Autowired
    private StatusService statusService;

    @MockBean
    private StatusRepository statusRepository;

    @Test
    public void testCreateStatus() {
        Status status = new Status();
        status.setStatus("Active");
        when(statusRepository.save(any(Status.class))).thenReturn(status);

        ResponseEntity<ApiResponse> response = statusService.createStatus(status);
        assertEquals("Status created successfully.", response.getBody().getMessage());
        assertEquals(true, response.getBody().isSuccess());
        assertEquals(status, response.getBody().getData());
    }

    @Test
    public void testGetAllStatuses() {
        Status status1 = new Status();
        Status status2 = new Status();
        when(statusRepository.findAll()).thenReturn(Arrays.asList(status1, status2));

        ResponseEntity<ApiResponse> response = statusService.getAllStatuses();
        assertEquals("Statuses retrieved successfully", response.getBody().getMessage());
        assertEquals(true, response.getBody().isSuccess());
        assertEquals(2, ((List<Status>) response.getBody().getData()).size());
    }

    @Test
    public void testGetStatusById() {
        Status status = new Status();
        status.setStatus_id(1L);
        when(statusRepository.findById(1L)).thenReturn(Optional.of(status));

        ResponseEntity<ApiResponse> response = statusService.getStatusById(1L);
        assertEquals("Status found.", response.getBody().getMessage());
        assertEquals(true, response.getBody().isSuccess());
        assertEquals(status, response.getBody().getData());
    }

    @Test
    public void testUpdateStatus() {
        Status existingStatus = new Status();
        existingStatus.setStatus_id(1L);
        existingStatus.setStatus("Active");

        Status newDetails = new Status();
        newDetails.setStatus("Inactive");

        when(statusRepository.findById(1L)).thenReturn(Optional.of(existingStatus));
        when(statusRepository.save(any(Status.class))).thenReturn(existingStatus);

        ResponseEntity<ApiResponse> response = statusService.updateStatus(1L, newDetails);
        assertEquals("Status updated successfully.", response.getBody().getMessage());
        assertEquals(true, response.getBody().isSuccess());
        verify(statusRepository).save(existingStatus);
    }

    @Test
    public void testDeleteStatus() {
        when(statusRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<ApiResponse> response = statusService.deleteStatus(1L);
        assertEquals("Status deleted successfully.", response.getBody().getMessage());
        assertEquals(true, response.getBody().isSuccess());
        verify(statusRepository, times(1)).deleteById(1L);
    }
}
