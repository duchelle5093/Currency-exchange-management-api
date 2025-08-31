package com.nathan.currencyconversionapi.exception;


/**
 * Custom exception for currency conversion related errors.
 * Thrown when issues occur during the conversion process.
 */
public class CurrencyConversionException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message
     */
    public CurrencyConversionException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public CurrencyConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
