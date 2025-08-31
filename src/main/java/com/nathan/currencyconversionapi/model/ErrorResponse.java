package com.nathan.currencyconversionapi.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class representing an error response to be sent to clients.
 * Used for standardized error handling throughout the API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    /**
     * HTTP status code
     */
    private int status;

    /**
     * Error message explaining what went wrong
     */
    private String message;

    /**
     * Timestamp of when the error occurred
     */
    private long timestamp;
}
