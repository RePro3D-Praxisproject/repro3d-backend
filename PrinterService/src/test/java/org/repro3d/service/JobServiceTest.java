package org.repro3d.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.repro3d.model.Job;
import org.repro3d.model.Item;
import org.repro3d.repository.ItemRepository;
import org.repro3d.repository.JobRepository;
import org.springframework.http.ResponseEntity;
import org.repro3d.utils.ApiResponse;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private JobService jobService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createJob_ItemNotFound() {
        Job job = new Job();
        job.setItem(new Item(1L, "Item1", "Description1", 30, "10x10x10", "file.gcode", "Plastic", "100", "test.com/image.jpg"));

        when(itemRepository.existsById(anyLong())).thenReturn(false);

        ResponseEntity<ApiResponse> response = jobService.createJob(job);

        assertFalse(response.getBody().isSuccess());
        assertEquals("Item not found for ID: 1", response.getBody().getMessage());
    }

    @Test
    void createJob_Successful() {
        Job job = new Job();
        job.setItem(new Item(1L, "Item1", "Description1", 30, "10x10x10", "file.gcode", "Plastic", "100", "test.com/image.jpg"));

        when(itemRepository.existsById(anyLong())).thenReturn(true);
        when(jobRepository.save(any(Job.class))).thenReturn(job);

        ResponseEntity<ApiResponse> response = jobService.createJob(job);

        assertTrue(response.getBody().isSuccess());
        assertEquals("Job created successfully.", response.getBody().getMessage());
    }

    @Test
    void getJobById_NotFound() {
        when(jobRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = jobService.getJobById(1L);

        assertFalse(response.getBody().isSuccess());
        assertEquals("Job not found for ID: 1", response.getBody().getMessage());
    }

    @Test
    void getJobById_Found() {
        Job job = new Job();
        when(jobRepository.findById(anyLong())).thenReturn(Optional.of(job));

        ResponseEntity<ApiResponse> response = jobService.getJobById(1L);

        assertTrue(response.getBody().isSuccess());
        assertEquals("Job found.", response.getBody().getMessage());
    }

    @Test
    void getAllJobs() {
        Job job1 = new Job();
        Job job2 = new Job();
        when(jobRepository.findAll()).thenReturn(Arrays.asList(job1, job2));

        ResponseEntity<ApiResponse> response = jobService.getAllJobs();

        assertTrue(response.getBody().isSuccess());
        assertEquals("Jobs retrieved successfully", response.getBody().getMessage());
    }

    @Test
    void updateJob_NotFound() {
        when(jobRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = jobService.updateJob(1L, new Job());

        assertFalse(response.getBody().isSuccess());
        assertEquals("Job not found for ID: 1", response.getBody().getMessage());
    }

    @Test
    void updateJob_FoundAndUpdated() {
        Job existingJob = new Job();
        Job newDetails = new Job();
        newDetails.setItem(new Item(1L, "Item1", "Description1", 30, "10x10x10", "file.gcode", "Plastic", "100", "test.com/image.jpg"));

        when(jobRepository.findById(anyLong())).thenReturn(Optional.of(existingJob));
        when(itemRepository.existsById(anyLong())).thenReturn(true);
        when(jobRepository.save(any(Job.class))).thenReturn(newDetails);

        ResponseEntity<ApiResponse> response = jobService.updateJob(1L, newDetails);

        assertTrue(response.getBody().isSuccess());
        assertEquals("Job updated successfully.", response.getBody().getMessage());
    }

    @Test
    void deleteJob_NotFound() {
        when(jobRepository.existsById(anyLong())).thenReturn(false);

        ResponseEntity<ApiResponse> response = jobService.deleteJob(1L);

        assertFalse(response.getBody().isSuccess());
        assertEquals("Job not found for ID: 1", response.getBody().getMessage());
    }

    @Test
    void deleteJob_Successful() {
        when(jobRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(jobRepository).deleteById(anyLong());

        ResponseEntity<ApiResponse> response = jobService.deleteJob(1L);

        assertTrue(response.getBody().isSuccess());
        assertEquals("Job deleted successfully.", response.getBody().getMessage());
    }
}
