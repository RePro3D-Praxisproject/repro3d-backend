package org.repro3d.service;

import org.repro3d.model.FrontConfig;
import org.repro3d.repository.FrontConfigRepository;
import org.springframework.stereotype.Service;

@Service
public class FrontConfigService {

    private FrontConfigRepository configRepository;

    public FrontConfigService(FrontConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public FrontConfig getConfigByKey(String key) {
        return configRepository.findByKey(key);
    }

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
