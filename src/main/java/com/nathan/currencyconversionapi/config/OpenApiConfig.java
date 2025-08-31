package com.nathan.currencyconversionapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Configuration class for OpenAPI 3.0 (Swagger) documentation.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Creates and returns a customized OpenAPI instance.
     *
     * @return A configured OpenAPI instance
     */
    @Bean
    public OpenAPI customOpenAPI() {


        // Create license information
        License mitLicense = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        // Define server
        Server localServer = new Server()
                .url("http://localhost:8080")
                .description("Local Development Server");

        // Create and return a customized OpenAPI instance
        return new OpenAPI()
                .info(new Info()
                        .title("Currency Conversion API")
                        .version("1.0")
                        .contact(contact)
                        .description("API that converts a sum of money from one currency to another using dynamically retrieved exchange rates from ExchangeRate API. Provides endpoints for converting currencies with real-time exchange rates.")
                        .license(mitLicense))
                .components(new Components())
                .servers(Arrays.asList(localServer));
    }
}