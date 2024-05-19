package org.repro3d.controller;

import org.repro3d.model.FrontConfig;
import org.repro3d.service.FrontConfigService;
import org.repro3d.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * FrontConfigController is a REST controller for managing front-end configurations.
 * It provides endpoints to retrieve and update configuration settings.
 */
@RestController
@RequestMapping("/api/config")
public class FrontConfigController {

    @Autowired
    private FrontConfigService configService;

    /**
     * Retrieves the configuration for the given key.
     *
     * @param key The key of the configuration to retrieve.
     * @return A ResponseEntity containing an ApiResponse with the configuration data if found,
     * or a message indicating that the configuration was not found.
     */
    @GetMapping("/{key}")
    public ResponseEntity<ApiResponse> getConfig(@PathVariable String key) {
        FrontConfig config = configService.getConfigByKey(key);
        if (config != null) {
            return ResponseEntity.ok(new ApiResponse(true, "Config retrieved successfully.", config));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "Config not found.", null));
        }
    }

    /**
     * Updates the configuration for the given key.
     *
     * @param frontConfig The FrontConfig object containing the key and value to update.
     * @return A ResponseEntity containing an ApiResponse with the updated configuration data.
     */
    @PostMapping("/{key}")
    public ResponseEntity<ApiResponse> updateConfig(@RequestBody FrontConfig frontConfig) {
        FrontConfig updatedConfig = configService.saveOrUpdateConfig(frontConfig.getKey(), frontConfig.getValue());
        return ResponseEntity.ok(new ApiResponse(true, "Config updated successfully.", updatedConfig));
    }
}
