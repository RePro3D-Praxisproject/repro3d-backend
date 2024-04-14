package repro3d.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import repro3d.model.Item;
import repro3d.model.Job;
import repro3d.repository.ItemRepository;
import repro3d.repository.JobRepository;
import repro3d.utils.ApiResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JobServiceTest {

    @Autowired
    private JobService jobService;

    @MockBean
    private JobRepository jobRepository;

    @MockBean
    private ItemRepository itemRepository;

    @Test
    public void testCreateJob() {
        Job job = new Job();
        Item item = new Item();
        item.setItem_id(1L); // Set the item ID as needed for the test
        job.setItem(item);
        job.setJob_id(1L);

        when(itemRepository.existsById(any(Long.class))).thenReturn(true); // Assuming the item exists.
        when(jobRepository.save(any(Job.class))).thenReturn(job);

        ResponseEntity<ApiResponse> response = jobService.createJob(job);
        assertEquals("Job created successfully.", response.getBody().getMessage());
        assertEquals(true, response.getBody().isSuccess());
        assertEquals(job, response.getBody().getData());
    }

    @Test
    public void testCreateJobItemNotFound() {
        Job job = new Job();
        Item item = new Item();
        item.setItem_id(1L);
        job.setItem(item);

        when(itemRepository.existsById(any(Long.class))).thenReturn(false); // Item does not exist.

        ResponseEntity<ApiResponse> response = jobService.createJob(job);
        assertEquals("Item not found for ID: " + item.getItem_id(), response.getBody().getMessage());
        assertEquals(false, response.getBody().isSuccess());
        verify(jobRepository, never()).save(any(Job.class)); // Ensure job is not saved.
    }

    @Test
    public void testGetJobById() {
        Job job = new Job();
        job.setJob_id(1L);
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

        ResponseEntity<ApiResponse> response = jobService.getJobById(1L);
        assertEquals("Job found.", response.getBody().getMessage());
        assertEquals(true, response.getBody().isSuccess());
        assertEquals(job, response.getBody().getData());
    }

    @Test
    public void testGetAllJobs() {
        Job job1 = new Job();
        job1.setJob_id(1L);
        Job job2 = new Job();
        job2.setJob_id(2L);
        when(jobRepository.findAll()).thenReturn(Arrays.asList(job1, job2));

        ResponseEntity<ApiResponse> response = jobService.getAllJobs();
        assertEquals("Jobs retrieved successfully", response.getBody().getMessage());
        assertEquals(true, response.getBody().isSuccess());
        assertEquals(Arrays.asList(job1, job2), response.getBody().getData());
    }

    @Test
    public void testUpdateJob() {
        Job existingJob = new Job();
        Item item = new Item();
        item.setItem_id(1L);
        existingJob.setItem(item);
        existingJob.setJob_id(1L);

        Job updatedDetails = new Job();
        Item updatedItem = new Item();
        updatedItem.setItem_id(2L);
        updatedDetails.setItem(updatedItem);

        when(jobRepository.findById(1L)).thenReturn(Optional.of(existingJob));
        when(itemRepository.existsById(2L)).thenReturn(true);
        when(jobRepository.save(any(Job.class))).thenReturn(existingJob);

        ResponseEntity<ApiResponse> response = jobService.updateJob(1L, updatedDetails);
        assertEquals("Job updated successfully.", response.getBody().getMessage());
        assertEquals(true, response.getBody().isSuccess());
        verify(jobRepository).save(existingJob);
    }

    @Test
    public void testUpdateJobItemNotFound() {
        Job existingJob = new Job();
        Item item = new Item();
        item.setItem_id(1L);
        existingJob.setItem(item);
        existingJob.setJob_id(1L);

        Job updatedDetails = new Job();
        Item updatedItem = new Item();
        updatedItem.setItem_id(2L);
        updatedDetails.setItem(updatedItem);

        when(jobRepository.findById(1L)).thenReturn(Optional.of(existingJob));
        when(itemRepository.existsById(2L)).thenReturn(false);

        ResponseEntity<ApiResponse> response = jobService.updateJob(1L, updatedDetails);
        assertEquals("Item not found for ID: " + updatedItem.getItem_id(), response.getBody().getMessage());
        assertEquals(false, response.getBody().isSuccess());
        verify(jobRepository, never()).save(any(Job.class));
    }

    @Test
    public void testDeleteJob() {
        when(jobRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<ApiResponse> response = jobService.deleteJob(1L);
        assertEquals("Job deleted successfully.", response.getBody().getMessage());
        assertEquals(true, response.getBody().isSuccess());
        verify(jobRepository).deleteById(1L);
    }
}
