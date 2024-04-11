package repro3d;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

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

}
