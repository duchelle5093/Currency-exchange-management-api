package com.nathan.currencyconversionapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Model class representing a currency conversion request.
 * Contains all the parameters required to perform a conversion.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversionRequest {

    /**
     * The source currency code (e.g., USD, EUR)
     */
    @NotBlank(message = "Source currency is required")
    private String sourceCurrency;

    /**
     * The target currency code to convert to (e.g., EUR, GBP)
     */
    @NotBlank(message = "Target currency is required")
    private String targetCurrency;

    /**
     * The amount of money to be converted
     */
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private Double amount;
}
