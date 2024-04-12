package repro3d.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repro3d.model.Job;
import repro3d.service.JobService;
import repro3d.utils.ApiResponse;

/**
 * Controller for managing {@code Job} entities.
 * <p>
 * Provides RESTful APIs for CRUD operations on jobs, including creating, retrieving,
 * updating, and deleting jobs.
 */
@RestController
@RequestMapping("/job")
public class JobController {

    private final JobService jobService;

    /**
     * Constructs a {@code JobController} with the necessary {@link JobService}.
     *
     * @param jobService The service used to perform operations on jobs.
     */
    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    /**
     * Creates a new job.
     *
     * @param job The job to create.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
     *         the result of the create operation.
     */
    @PostMapping
    public ResponseEntity<ApiResponse> createJob(@RequestBody Job job) {
        return jobService.createJob(job);
    }

    /**
     * Retrieves a job by its ID.
     *
     * @param id The ID of the job to retrieve.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the job
     *         if found, or an error message otherwise.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getJobById(@PathVariable Long id) {
        return jobService.getJobById(id);
    }

    /**
     * Retrieves all jobs.
     *
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the list
     *         of all jobs. If no jobs are found, returns an empty list.
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getAllJobs() {
        return jobService.getAllJobs();
    }

    /**
     * Updates the details of an existing job.
     *
     * @param id  The ID of the job to update.
     * @param job The new details for the job.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
     *         the result of the update operation.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateJob(@PathVariable Long id, @RequestBody Job job) {
        return jobService.updateJob(id, job);
    }

    /**
     * Deletes a job by its ID.
     *
     * @param id The ID of the job to delete.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
     *         the result of the delete operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteJob(@PathVariable Long id) {
        return jobService.deleteJob(id);
    }
}
