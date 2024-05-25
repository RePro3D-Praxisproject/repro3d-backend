package org.repro3d.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletResponse;
import org.repro3d.model.Job;
import org.repro3d.model.Status;
import org.repro3d.repository.JobRepository;
import org.repro3d.utils.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.repro3d.model.Printer;
import org.repro3d.repository.PrinterRepository;
import org.repro3d.utils.ApiResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
//import java.time.Duration;
import java.util.List;

/**
 * Service class for handling operations related to {@link Printer} entities.
 * <p>
 * Encapsulates the business logic for creating, retrieving, updating, and deleting printers,
 * interacting with the {@link PrinterRepository} to perform these operations.
 */
@Service
public class PrinterService {

    private final PrinterRepository printerRepository;


    private final JobRepository jobRepository;

    private final WebClient webClient;

    private final ObjectMapper objectMapper;
    /**
     * Constructs a {@code PrinterService} with the necessary {@link PrinterRepository}.
     *
     * @param printerRepository The repository used for data operations on printers.
     * @param jobRepository
     * @param objectMapper
     */
    @Autowired
    public PrinterService(PrinterRepository printerRepository, JobRepository jobRepository, WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.printerRepository = printerRepository;
        this.jobRepository = jobRepository;
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
    }
    /**
     * Creates and saves a new printer in the repository.
     *
     * @param printer The printer to be created and saved.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the result of the create operation.
     */
    public ResponseEntity<ApiResponse> createPrinter(Printer printer) {
        Printer savedPrinter = printerRepository.save(printer);
        return ResponseEntity.ok(new ApiResponse(true, "Printer created successfully.", savedPrinter));
    }

    /**
     * Retrieves all printers.
     *
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with a list
     *         of all printers.
     */
    public ResponseEntity<ApiResponse> getAllPrinters() {
        List<Printer> printers = printerRepository.findAll();
        return ResponseEntity.ok(new ApiResponse(true, "Printers retrieved successfully", printers));
    }

    /**
     * Retrieves a printer by its ID.
     *
     * @param id The ID of the printer to retrieve.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the printer if found, or an error message otherwise.
     */
    public ResponseEntity<ApiResponse> getPrinterById(Long id) {
        return printerRepository.findById(id)
                .map(printer -> ResponseEntity.ok(new ApiResponse(true, "Printer found.", printer)))
                .orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Printer not found for ID: " + id, null)));
    }

    /**
     * Updates an existing printer identified by its ID with new details.
     *
     * @param id The ID of the printer to update.
     * @param printerDetails The new details for the printer.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the updated printer, or an error message if the printer was not found.
     */
    public ResponseEntity<ApiResponse> updatePrinter(Long id, Printer printerDetails) {
        return printerRepository.findById(id)
                .map(printer -> {
                    printer.setName(printerDetails.getName());
                    printer.setLocation(printerDetails.getLocation());
                    printer.setIp_addr(printerDetails.getIp_addr());
                    printer.setApikey(printerDetails.getApikey());
                    Printer updatedPrinter = printerRepository.save(printer);
                    return ResponseEntity.ok(new ApiResponse(true, "Printer updated successfully.", updatedPrinter));
                }).orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Printer not found for ID: " + id, null)));
    }

    /**
     * Deletes a printer from the repository identified by its ID.
     *
     * @param id The ID of the printer to delete.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating the result of the delete operation.
     */
    public ResponseEntity<ApiResponse> deletePrinter(Long id) {
        return printerRepository.findById(id)
                .map(printer -> {
                    printerRepository.deleteById(id);
                    return ResponseEntity.ok(new ApiResponse(true, "Printer deleted successfully.", null));
                }).orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Printer not found for ID: " + id, null)));
    }

    /**
     * Retrieves the API key for a specific printer identified by its ID.
     *
     * @param id The ID of the printer whose API key is to be retrieved.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse}. The ApiResponse will include a success status
     *         and the API key if the printer is found and the API key is available, or an error message otherwise.
     */
    public ResponseEntity<ApiResponse> getApiKeyById(Long id){
        return printerRepository.findById(id)
                .map(printer -> {
                    String apiKey = printer.getApikey();
                    if (apiKey == null || apiKey.isEmpty()) {
                        return ResponseEntity.ok(new ApiResponse(false, "API Key not available for the requested printer.", null));
                    }
                    return ResponseEntity.ok(new ApiResponse(true, "API Key retrieved successfully.", apiKey));
                }).orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Printer not found for ID: " + id, null)));
    }


    public boolean isPrinterAvailable(Printer printer) {
        String url = "http://" + printer.getIp_addr() + "/api/printer";
        try {
            System.out.println("Checking printer availability at: " + url);
            String response = webClient.get()
                    .uri(url)
                    .header("X-Api-Key", printer.getApikey())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            System.out.println("Response: " + response);

            JsonNode jsonResponse = objectMapper.readTree(response);
            String state = jsonResponse.path("state").path("text").asText();
            System.out.println("Printer state: " + state);

            return "Operational".equalsIgnoreCase(state);
        } catch (Exception e) {
            System.out.println("Error checking printer availability: " + e.getMessage());
            return false;
        }
    }

    public boolean startPrintJob(Printer printer, Job job) {
        String jobStartUrl = "http://" + printer.getIp_addr() + "/api/job";

        try {
            System.out.println("Starting print job at: " + jobStartUrl);
            ObjectNode startRequest = objectMapper.createObjectNode();
            startRequest.put("command", "start");
            startRequest.put("file", job.getItem().getFile_ref());
            System.out.println("Start request: " + startRequest.toString());

            String response = webClient.post()
                    .uri(jobStartUrl)
                    .header("X-Api-Key", printer.getApikey())
                    .bodyValue(startRequest.toString())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            System.out.println("Start job response: " + response);

            return true;
        } catch (Exception e) {
            System.out.println("Error starting print job: " + e.getMessage());
            return false;
        }
    }


    public boolean isJobComplete(Printer printer, Job job) {
        String jobStatusUrl = "http://" + printer.getIp_addr() + "/api/job";

        try {
            System.out.println("Checking job completion at: " + jobStatusUrl);
            String response = webClient.get()
                    .uri(jobStatusUrl)
                    .header("X-Api-Key", printer.getApikey())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            System.out.println("Response: " + response);

            JsonNode jsonResponse = objectMapper.readTree(response);
            String state = jsonResponse.path("state").path("text").asText();
            System.out.println("Job state: " + state);

            return "Operational".equalsIgnoreCase(state);
        } catch (Exception e) {
            System.out.println("Error checking job completion: " + e.getMessage());
            return false;
        }
    }

    public void completeJob(Job job) {
        System.out.println("Completing job ID: " + job.getJobId());
        job.setStatus(new Status(3L, "Done"));
        jobRepository.save(job);
    }

    public List<Printer> getPrinters() {
        System.out.println("Retrieving all printers.");
        return printerRepository.findAll();
    }


    /**
     * Streams live webcam footage from a specified printer using its IP address and API key.
     * This method retrieves the printer details from the database, checks for the necessary
     * configuration (IP address and API key), and initiates a streaming session directly to the
     * client's response stream.
     *
     * This method handles the streaming by setting appropriate headers on the HttpServletResponse
     * and pushing the webcam data in real-time until the streaming is terminated or encounters an error.
     *
     * @param id The ID of the printer from which to stream the webcam footage. The printer must be
     *           registered in the system with a valid IP address and API key.
     * @param response The HttpServletResponse through which the webcam stream is sent to the client.
     *                 This method sets the status and content type directly on this response object.
     * @return A ResponseEntity containing an ApiResponse. If the streaming starts successfully, it returns
     *         a success ApiResponse. If the printer is not found, or if it lacks an IP address or API key,
     *         it returns an error ApiResponse.
     *
     * @throws RuntimeException If an IOException occurs during streaming, it is wrapped and rethrown as
     *                          a RuntimeException to simplify error handling further up the call stack.
     *                          // Should be handled by the {@link GlobalExceptionHandler}
     */
    public ResponseEntity<ApiResponse> streamWebcam(Long id, HttpServletResponse response) {
        return printerRepository.findById(id)
                .map(printer -> {
                    String ipAddress = printer.getIp_addr();
                    String apiKey = printer.getApikey();
                    if (ipAddress != null && apiKey != null) {
                        WebClient webClient = WebClient.builder().build();
                        try {
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.setContentType("multipart/x-mixed-replace; boundary=boundarydonotcross");

                            webClient.get()
                                    .uri("http://{ipAddress}/webcam/?action=stream&apikey={apiKey}", ipAddress, apiKey)
                                    .accept(MediaType.parseMediaType("multipart/x-mixed-replace; boundary=boundarydonotcross"))
                                    .retrieve()
                                    .bodyToFlux(DataBuffer.class)
                                    // .timeout(Duration.ofSeconds(10))
                                    .doOnNext(dataBuffer -> {
                                        try (final var input = dataBuffer.asInputStream()) {
                                            input.transferTo(response.getOutputStream());
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        } finally {
                                            DataBufferUtils.release(dataBuffer);
                                        }
                                    })
                                    .blockLast();
                            return ResponseEntity.ok(new ApiResponse(true, "Webcam streaming initiated successfully.", null));
                        } catch (Exception e) {
                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .body(new ApiResponse(false, "Failed to initiate webcam streaming for ID: " + id, null));
                        }
                    } else {
                        return ResponseEntity.badRequest()
                                .body(new ApiResponse(false, "IP address or API key not available for the requested printer.", null));
                    }
                }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(false, "Printer not found for ID: " + id, null)));
    }
}
