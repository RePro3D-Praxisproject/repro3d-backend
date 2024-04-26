package org.repro3d;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main application class for BillingService.
 * Uses DiscoveryClient.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EntryPointBillingService {

    /**
     * Main method to start the AuthService application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(EntryPointBillingService.class, args);
    }

}
