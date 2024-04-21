package org.repro3d.discoveryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EntryPointDiscoveryServer {

    /**
     * Main method to start the DiscoveryServer application.
     *
     * @param args Command line arguments passed during the application start.
     */
    public static void main(String[] args) {
        SpringApplication.run(EntryPointDiscoveryServer.class, args);
    }
}