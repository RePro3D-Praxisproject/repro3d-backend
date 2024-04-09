package org.repro3d.utils;

/**
 * Represents a standardized response structure for API calls.
 * This class encapsulates the details of the response, including success status, a message, and any associated data.
 */
public class ApiResponse {

    /**
     * Indicates the success or failure of the API call.
     */
    private boolean success;

    /**
     * A message providing additional details about the API call,
     * typically used for providing information on errors or confirmation messages.
     */
    private String message;

    /**
     * The data payload returned by the API call.
     * This can be any type of object, including null, depending on the context of the API response.
     */
    private Object data;

    /**
     * Constructs a new ApiResponse with specified success status, message, and data.
     *
     * @param success A boolean indicating the success status of the API call.
     * @param message A String containing additional information about the API response.
     * @param data The data object containing the payload of the API response.
     */
    public ApiResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    /**
     * Returns the success status of the API response.
     *
     * @return A boolean representing the success status.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the success status of the API response.
     *
     * @param success A boolean representing the new success status.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Returns the message associated with the API response.
     *
     * @return A String containing the response message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message for the API response.
     *
     * @param message A String containing the new message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the data object of the API response.
     *
     * @return An Object representing the data of the API response.
     */
    public Object getData() {
        return data;
    }

    /**
     * Sets the data object for the API response.
     *
     * @param data An Object representing the new data for the API response.
     */
    public void setData(Object data) {
        this.data = data;
    }
}
