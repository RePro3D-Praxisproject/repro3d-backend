package repro3d;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main application class for OrderService.
 * Uses DiscoveryClient.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EntryPointOrderService {

    /**
     * Main method to start the OrderService application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(EntryPointOrderService.class, args);
    }

}
