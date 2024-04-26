package org.repro3d.controller;

import org.repro3d.model.RedeemCode;
import org.repro3d.service.RedeemCodeService;
import org.repro3d.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redeem-code")
public class RedeemCodeController {
    private final RedeemCodeService redeemCodeService;

    @Autowired
    public RedeemCodeController(RedeemCodeService redeemCodeService) {
        this.redeemCodeService = redeemCodeService;
    }