package com.nathan.currencyconversionapi.controller;

import com.nathan.currencyconversionapi.model.ConversionRequest;
import com.nathan.currencyconversionapi.model.ConversionResult;
import com.nathan.currencyconversionapi.model.ErrorResponse;
import com.nathan.currencyconversionapi.service.CurrencyConversionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * REST controller for currency conversion operations.
 * Provides endpoints for converting currencies using real-time exchange rates.
 */
@RestController
@RequestMapping("/api/currency")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Currency Conversion API", description = "API endpoints for converting money between different currencies using real-time exchange rates from ExchangeRate API")
public class CurrencyConversionController {

    /**
     * Service for handling currency conversion business logic
     */
    private final CurrencyConversionService conversionService;

    /**
     * Endpoint for converting an amount from one currency to another using POST method.
     *
     * @param request The conversion request containing source, target currencies and amount
     * @return ResponseEntity containing the conversion result
     */
    @PostMapping(
            value = "/convert",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Convert currency (POST method)",
            description = "Converts an amount from one currency to another using current exchange rates. " +
                    "This endpoint accepts a JSON request body with source currency, target currency, and amount."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Conversion successful",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ConversionResult.class),
                            examples = @ExampleObject(
                                    value = "{\"sourceCurrency\":\"USD\",\"targetCurrency\":\"EUR\",\"sourceAmount\":100.0,\"targetAmount\":91.68,\"exchangeRate\":0.9168,\"timestamp\":\"2025-05-02T10:15:30.123\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request - This occurs when the request parameters are invalid",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{\"status\":400,\"message\":\"Amount must be greater than zero\",\"timestamp\":1714640530123}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Currency not found - This occurs when the provided currency code doesn't exist",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error - This occurs when there's an issue with the server or external API",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public Mono<ResponseEntity<ConversionResult>> convertCurrency(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Conversion request containing source currency, target currency, and amount to convert",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ConversionRequest.class),
                            examples = @ExampleObject(
                                    value = "{\"sourceCurrency\":\"USD\",\"targetCurrency\":\"EUR\",\"amount\":100.0}"
                            )
                    )
            )
            @Valid @RequestBody ConversionRequest request) {

        log.info("Received conversion request: {}", request);
        return conversionService.convertCurrency(request)
                .map(result -> {
                    log.info("Conversion successful: {}", result);
                    return ResponseEntity.ok(result);
                });
    }

    /**
     * Alternative endpoint for converting currency using path variables and query parameter.
     *
     * @param sourceCurrency The source currency code
     * @param targetCurrency The target currency code
     * @param amount The amount to convert
     * @return ResponseEntity containing the conversion result
     */
    @GetMapping("/convert/{sourceCurrency}/to/{targetCurrency}")
    @Operation(
            summary = "Convert currency (GET method)",
            description = "Alternative endpoint for currency conversion using path variables for currencies and query parameter for amount. " +
                    "This provides a more RESTful and URL-friendly approach for simple conversions."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Conversion successful",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ConversionResult.class),
                            examples = @ExampleObject(
                                    value = "{\"sourceCurrency\":\"USD\",\"targetCurrency\":\"EUR\",\"sourceAmount\":100.0,\"targetAmount\":91.68,\"exchangeRate\":0.9168,\"timestamp\":\"2025-05-02T10:15:30.123\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request - This occurs when the amount is invalid or missing",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Currency not found - This occurs when one of the provided currency codes doesn't exist",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error - This occurs when there's an issue with the server or external API",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public Mono<ResponseEntity<ConversionResult>> convertCurrencyAlternative(
            @Parameter(
                    description = "The 3-letter ISO currency code of the source currency (e.g., USD, EUR, GBP)",
                    required = true,
                    example = "USD"
            )
            @PathVariable String sourceCurrency,

            @Parameter(
                    description = "The 3-letter ISO currency code of the target currency (e.g., EUR, JPY, GBP)",
                    required = true,
                    example = "EUR"
            )
            @PathVariable String targetCurrency,

            @Parameter(
                    description = "The amount to convert (must be greater than 0)",
                    required = true,
                    example = "100.0"
            )
            @RequestParam Double amount) {

        ConversionRequest request = new ConversionRequest(sourceCurrency, targetCurrency, amount);
        log.info("Received alternative conversion request: {}", request);

        return conversionService.convertCurrency(request)
                .map(result -> {
                    log.info("Conversion successful: {}", result);
                    return ResponseEntity.ok(result);
                });
    }

    /**
     * Endpoint to get the list of supported currencies.
     * This is a useful reference endpoint for frontend applications.
     *
     * @return ResponseEntity containing an array of supported currency codes
     */
    @GetMapping("/supported-currencies")
    @Operation(
            summary = "Get supported currencies",
            description = "Returns a list of all supported currency codes that can be used for conversion. " +
                    "This endpoint is useful for populating dropdowns or autocomplete fields in frontend applications."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of supported currencies retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    value = "[\"USD\", \"EUR\", \"GBP\", \"JPY\", \"AUD\", \"CAD\", \"CHF\", \"CNY\", \"INR\"]"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public Mono<ResponseEntity<String[]>> getSupportedCurrencies() {
        // This is a placeholder - in a real implementation, you would get this from your service
        // Here we're just returning a hardcoded list of common currencies
        String[] supportedCurrencies = {"USD", "EUR", "GBP", "JPY", "AUD", "CAD", "CHF", "CNY", "INR"};

        return Mono.just(ResponseEntity.ok(supportedCurrencies));
    }
}