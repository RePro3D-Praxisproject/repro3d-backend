package org.repro3d.service;

import org.repro3d.model.Status;
import org.repro3d.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.repro3d.model.Job;
import org.repro3d.repository.ItemRepository;
import org.repro3d.repository.JobRepository;
import org.repro3d.utils.ApiResponse;

import java.util.List;
import java.util.Optional;

/**
 * Service class for handling operations related to {@link Job} entities.
 * <p>
 * Encapsulates the business logic for creating, retrieving, updating, and deleting jobs,
 * interacting with the {@link JobRepository} to perform these operations.
 */
@Service
public class JobService {

    private final JobRepository jobRepository;
    private final ItemRepository itemRepository;

    private StatusRepository statusRepository;

    /**
     * Constructs a {@code JobService} with the necessary {@link JobRepository}.
     *
     * @param jobRepository    The repository used for data operations on jobs.
     * @param statusRepository
     */
    @Autowired
    public JobService(JobRepository jobRepository, ItemRepository itemRepository, StatusRepository statusRepository) {
        this.jobRepository = jobRepository;
        this.itemRepository = itemRepository;
        this.statusRepository = statusRepository;
    }

    /**
     * Creates and saves a new job in the repository.
     *
     * @param job The job to be created and saved.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the result of the create operation.
     */
    public ResponseEntity<ApiResponse> createJob(Job job) {
        if (job.getItem() != null && !itemRepository.existsById(job.getItem().getItem_id())) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Item not found for ID: " + job.getItem().getItem_id(), null));
        }
        Job savedJob = jobRepository.save(job);
        return ResponseEntity.ok(new ApiResponse(true, "Job created successfully.", savedJob));
    }

    /**
     * Retrieves a job by its ID.
     *
     * @param id The ID of the job to retrieve.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the job if found, or an error message otherwise.
     */
    public ResponseEntity<ApiResponse> getJobById(Long id) {
        Optional<Job> job = jobRepository.findById(id);
        if (job.isPresent()) {
            return ResponseEntity.ok(new ApiResponse(true, "Job found.", job.get()));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "Job not found for ID: " + id, null));
        }
    }

    /**
     * Retrieves all jobs.
     *
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with a list of all jobs.
     */
    public ResponseEntity<ApiResponse> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();
        return ResponseEntity.ok(new ApiResponse(true, "Jobs retrieved successfully", jobs));
    }

    /**
     * Updates an existing job identified by its ID with new details.
     *
     * @param id The ID of the job to update.
     * @param jobDetails The new details for the job.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the updated job, or an error message if the job was not found.
     */
    public ResponseEntity<ApiResponse> updateJob(Long id, Job jobDetails) {
        return jobRepository.findById(id)
                .map(job -> {
                    if (jobDetails.getItem() != null && !itemRepository.existsById(jobDetails.getItem().getItem_id())) {
                        return ResponseEntity.badRequest().body(new ApiResponse(false, "Item not found for ID: " + jobDetails.getItem().getItem_id(), null));
                    }
                    job.setItem(jobDetails.getItem());
                    job.setPrinter(jobDetails.getPrinter());
                    job.setStatus(jobDetails.getStatus());
                    job.setStart_date(jobDetails.getStart_date());
                    job.setEnd_date(jobDetails.getEnd_date());
                    Job updatedJob = jobRepository.save(job);
                    return ResponseEntity.ok(new ApiResponse(true, "Job updated successfully.", updatedJob));
                }).orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Job not found for ID: " + id, null)));
    }

    /**
     * Updates the status of a job to 'Done' if its current status is 'Awaiting Pick Up'.
     * This method retrieves the job by its ID and checks if the current status ID is 3 (representing 'Awaiting Pick Up').
     * If the status is 'Awaiting Pick Up', it updates the status to 4 (representing 'Done') and saves the updated job in the repository.
     * If the status is not 'Awaiting Pick Up', it returns a bad request response.
     * If the job is not found, it returns a response indicating that the job was not found.
     *
     * @param id The ID of the job to update.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the updated job, or an error message if the job was not found or if the status transition is not allowed.
     */
    public ResponseEntity<ApiResponse> markAsDone(Long id) {
        return jobRepository.findById(id)
                .map(job -> {
                    if (job.getStatus().getStatus_id() == 3L) {
                        Status newStatus = new Status();
                        newStatus.setStatus_id(4L);
                        job.setStatus(newStatus);
                        Job updatedJob = jobRepository.save(job);
                        return ResponseEntity.ok(new ApiResponse(true, "Job status updated successfully.", updatedJob));
                    } else {
                        return ResponseEntity.badRequest().body(new ApiResponse(false, "Job status is not 'Awaiting Pick Up', cannot update to 'Done'.", null));
                    }
                }).orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Job not found for ID: " + id, null)));
    }

    /**
     * Deletes a job from the repository identified by its ID.
     *
     * @param id The ID of the job to delete.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating the result of the delete operation.
     */
    public ResponseEntity<ApiResponse> deleteJob(Long id) {
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse(true, "Job deleted successfully.", null));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "Job not found for ID: " + id, null));
        }
    }
}
