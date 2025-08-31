package com.nathan.currencyconversionapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Model class representing the result of a currency conversion.
 * Contains all the details of the conversion including source and target amounts,
 * exchange rate, and timestamp of the conversion.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ConversionResult {

    /**
     * The source currency code (e.g., USD, EUR)
     */
    @NotBlank(message = "Source currency is required")
    private String sourceCurrency;

    /**
     * The target currency code (e.g., EUR, GBP)
     */
    @NotBlank(message = "Target currency is required")
    private String targetCurrency;

    /**
     * The amount in source currency
     */
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private Double sourceAmount;

    /**
     * The converted amount in target currency
     */
    private Double targetAmount;

    /**
     * The exchange rate used for the conversion
     */
    private Double exchangeRate;

    /**
     * Timestamp of when the conversion was performed
     */
    private LocalDateTime timestamp;
}