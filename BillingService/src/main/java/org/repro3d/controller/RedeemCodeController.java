package org.repro3d.controller;

import org.repro3d.model.RedeemCode;
import org.repro3d.service.RedeemCodeService;
import org.repro3d.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redeem-code")
public class RedeemCodeController {

    private final RedeemCodeService redeemCodeService;

    @Autowired
    public RedeemCodeController(RedeemCodeService redeemCodeService) {
        this.redeemCodeService = redeemCodeService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createRedeemCode(@RequestBody RedeemCode redeemCode) {
        return redeemCodeService.createRedeemCode(redeemCode);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getRedeemCodeById(@PathVariable Long id) {
        return redeemCodeService.getRedeemCodeById(id);
    }

    @GetMapping("/generate")
    public ResponseEntity<ApiResponse> generateRedeemCode() {
        return redeemCodeService.generateRedeemCode();
    }

    @GetMapping("/validate")
    public ResponseEntity<ApiResponse> validateRedeemCode(@RequestHeader("RC-Code") String code) {
        return redeemCodeService.validateRedeemCode(code);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllRedeemCodes() {
        return redeemCodeService.getAllRedeemCodes();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateRedeemCode(@PathVariable Long id, @RequestBody RedeemCode redeemCodeDetails) {
        return redeemCodeService.updateRedeemCode(id, redeemCodeDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteRedeemCode(@PathVariable Long id) {
        return redeemCodeService.deleteRedeemCode(id);
    }
}
