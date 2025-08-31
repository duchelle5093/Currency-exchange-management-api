package com.nathan.currencyconversionapi.model;

import lombok.Data;
import java.util.Map;

/**
 * Model class representing the response from the ExchangeRate API.
 * This matches the JSON structure returned by the API.
 */
@Data
public class ExchangeRateApiResponse {

    /**
     * Result status from the API ("success" or "error")
     */
    private String result;

    /**
     * Documentation URL
     */
    private String documentation;

    /**
     * Terms of use URL
     */
    private String terms_of_use;

    /**
     * Timestamp of when the rates were last updated (Unix format)
     */
    private long time_last_update_unix;

    /**
     * Timestamp of when the rates were last updated (UTC string format)
     */
    private String time_last_update_utc;

    /**
     * Timestamp of when the rates will next update (Unix format)
     */
    private long time_next_update_unix;

    /**
     * Timestamp of when the rates will next update (UTC string format)
     */
    private String time_next_update_utc;

    /**
     * Base currency code used for the rates
     */
    private String base_code;

    /**
     * Map containing currency codes as keys and their exchange rates as values
     */
    private Map<String, Double> conversion_rates;

    /**
     * Convenience method to check if the response was successful
     */
    public boolean isSuccess() {
        return "success".equals(result);
    }

    /**
     * Convenience method to get rates with a familiar name
     */
    public Map<String, Double> getRates() {
        return conversion_rates;
    }
}