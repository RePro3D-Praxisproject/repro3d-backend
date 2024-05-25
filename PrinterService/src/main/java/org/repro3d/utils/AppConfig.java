package org.repro3d.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for application-wide beans.
 * <p>
 * This class provides the configuration for beans that will be used
 * throughout the application, ensuring they are properly instantiated
 * and managed by the Spring container.
 */
@Configuration
public class AppConfig {

    /**
     * Creates and returns an {@link ObjectMapper} bean.
     * <p>
     * This bean is used for JSON serialization and deserialization throughout
     * the application.
     *
     * @return a new instance of {@link ObjectMapper}
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}