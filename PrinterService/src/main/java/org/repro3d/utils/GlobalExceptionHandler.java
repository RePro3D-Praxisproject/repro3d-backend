package org.repro3d.utils;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Provides global exception handling for the application. This handler catches various types
 * of exceptions thrown by the PrinterService module and returns standardized API responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions related to database integrity violations such as constraint violations.
     *
     * @param ex The caught DataIntegrityViolationException.
     * @return A ResponseEntity containing a sanitized error message and a CONFLICT status.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return new ResponseEntity<>(new ApiResponse(false, sanitizeErrorMessage("Database error: " + ex.getRootCause().getMessage()), null), HttpStatus.CONFLICT);
    }

    /**
     * Handles exceptions thrown due to illegal arguments or illegal state in the PrinterService module.
     *
     * @param ex The RuntimeException that was caught, typically IllegalArgumentException or IllegalStateException.
     * @return A ResponseEntity containing a sanitized error message and a BAD_REQUEST status.
     */
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<ApiResponse> handleBadRequestExceptions(RuntimeException ex) {
        return new ResponseEntity<>(new ApiResponse(false, sanitizeErrorMessage(ex.getMessage()), null), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles exceptions related to validation failures of method arguments marked with @Valid.
     *
     * @param ex The MethodArgumentNotValidException that was caught.
     * @return A ResponseEntity containing a sanitized validation error message and a BAD_REQUEST status.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");
        return new ResponseEntity<>(new ApiResponse(false, sanitizeErrorMessage(message), null), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles HTTP method request exceptions when a requested method is not supported.
     *
     * @param ex The HttpRequestMethodNotSupportedException that was caught.
     * @return A ResponseEntity containing a sanitized error message and a METHOD_NOT_ALLOWED status.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return new ResponseEntity<>(new ApiResponse(false, sanitizeErrorMessage("Method not supported: " + ex.getMessage()), null), HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Handles client HTTP errors captured by the HttpClientErrorException.
     *
     * @param ex The HttpClientErrorException that was caught.
     * @return A ResponseEntity containing a sanitized error message and an appropriate HTTP status.
     */
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ApiResponse> handleHttpClientError(HttpClientErrorException ex) {
        return new ResponseEntity<>(new ApiResponse(false, sanitizeErrorMessage("HTTP Error: " + ex.getStatusText()), null), HttpStatus.valueOf(ex.getRawStatusCode()));
    }

    /**
     * Provides a fallback method to handle all other exceptions that do not have specific handlers.
     *
     * @param ex The Exception that was caught.
     * @return A ResponseEntity containing a sanitized error message and an INTERNAL_SERVER_ERROR status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGlobalExceptions(Exception ex) {
        return new ResponseEntity<>(new ApiResponse(false, sanitizeErrorMessage("An error occurred: " + ex.getMessage()), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Sanitizes error messages to remove sensitive data such as IP addresses.
     *
     * @param errorMessage The original error message.
     * @return A sanitized version of the error message.
     */
    private String sanitizeErrorMessage(String errorMessage) {
        return errorMessage.replaceAll("(\\d+\\.\\d+\\.\\d+\\.\\d+)", "[IP]");
    }
}
