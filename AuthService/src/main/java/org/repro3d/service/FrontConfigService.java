package org.repro3d.service;

import org.repro3d.model.FrontConfig;
import org.repro3d.repository.FrontConfigRepository;
import org.springframework.stereotype.Service;

/**
 * FrontConfigService provides services for managing front-end configuration settings.
 * It includes methods to retrieve, save, and update configurations.
 */
@Service
public class FrontConfigService {


    private FrontConfigRepository configRepository;

    /**
     * Constructor for FrontConfigService.
     *
     * @param configRepository The repository to interact with the FrontConfig entity.
     */
    public FrontConfigService(FrontConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    /**
     * Retrieves the configuration for the given key.
     *
     * @param key The key of the configuration to retrieve.
     * @return The FrontConfig entity with the specified key.
     */
    public FrontConfig getConfigByKey(String key) {
        return configRepository.findByKey(key);
    }

    /**
     * Saves or updates the configuration for the given key and value.
     * If a configuration with the specified key exists, its value is updated; otherwise, a new configuration is created.
     *
     * @param key   The key of the configuration to save or update.
     * @param value The value of the configuration to save or update.
     * @return The saved or updated FrontConfig entity.
     */
    public FrontConfig saveOrUpdateConfig(String key, String value) {
        FrontConfig config = configRepository.findByKey(key);
        if (config == null) {
            config = new FrontConfig(key, value);
        } else {
            config.setValue(value);
        }
        return configRepository.save(config);
    }
}
