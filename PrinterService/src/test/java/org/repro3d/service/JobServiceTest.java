package org.repro3d.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.repro3d.model.Job;
import org.repro3d.model.Item;
import org.repro3d.repository.ItemRepository;
import org.repro3d.repository.JobRepository;
import org.repro3d.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private JobService jobService;

    private Job job;
    private Item item;

    @BeforeEach
    void setUp() {
        item = new Item();
        item.setItem_id(1L);

        job = new Job();
        job.setJob_id(1L);
        job.setItem(item);
    }

    @Test
    void testCreateJobWithNonExistingItem() {
        when(itemRepository.existsById(anyLong())).thenReturn(false);

        ResponseEntity<ApiResponse> response = jobService.createJob(job);

        assertEquals(400, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Item not found for ID: " + job.getItem().getItem_id(), response.getBody().getMessage());
    }

    @Test
    void testGetJobByIdWhenJobExists() {
        when(jobRepository.findById(anyLong())).thenReturn(Optional.of(job));

        ResponseEntity<ApiResponse> response = jobService.getJobById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals(job, response.getBody().getData());
    }

    @Test
    void testGetJobByIdWhenJobDoesNotExist() {
        when(jobRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = jobService.getJobById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertNull(response.getBody().getData());
        assertEquals("Job not found for ID: 1", response.getBody().getMessage());
    }

}
