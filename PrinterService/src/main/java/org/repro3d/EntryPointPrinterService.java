package org.repro3d;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Main application class for PrinterService.
 * Uses DiscoveryClient.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EntryPointPrinterService {

    /**
     * Main method to start the PrinterService application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(EntryPointPrinterService.class, args);
    }

    // Remove this, when not needed anymore.
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:9000");
            }
        };
    }
}

