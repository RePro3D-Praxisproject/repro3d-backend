package org.repro3d.controller;

import org.repro3d.model.FrontConfig;
import org.repro3d.service.FrontConfigService;
import org.repro3d.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/config")
public class FrontConfigController {

    @Autowired
    private FrontConfigService configService;

    @GetMapping("/{key}")
    public ResponseEntity<ApiResponse> getConfig(@PathVariable String key) {
        FrontConfig config = configService.getConfigByKey(key);
        if (config != null) {
            return ResponseEntity.ok(new ApiResponse(true, "Config retrieved successfully.", config));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "Config not found.", null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> updateConfig(@RequestBody FrontConfig frontConfig) {
        FrontConfig updatedConfig = configService.saveOrUpdateConfig(frontConfig.getKey(), frontConfig.getValue());
        return ResponseEntity.ok(new ApiResponse(true, "Config updated successfully.", updatedConfig));
    }
}
