package com.agent.middleware.controller;

import com.agent.middleware.dto.RefCodeTypeMaintenanceDto;
import com.agent.middleware.service.RefCodeTypeMaintenanceService;
import com.bbl.util.model.GenDataBlock;
import com.bbl.util.model.ListBlock;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class RefCodeTypeMaintenanceController {
    private final RefCodeTypeMaintenanceService refCodeTypeMaintenanceService;
    public RefCodeTypeMaintenanceController(RefCodeTypeMaintenanceService refCodeTypeMaintenanceService) {
        this.refCodeTypeMaintenanceService = refCodeTypeMaintenanceService;
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/get-refType-list")
    public ListBlock getRefCodeTypeList(@RequestBody RefCodeTypeMaintenanceDto dto) {
        System.out.println(dto);
        return refCodeTypeMaintenanceService.getRefCodeTypeList(dto.getFunctionCode(),dto.getRefCodeType());
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/get-refType-detail")
    public GenDataBlock getRefTypeDetail(@RequestBody RefCodeTypeMaintenanceDto dto) {
        return refCodeTypeMaintenanceService.getRefCodeTypeDetail(dto.getFunctionCode(),dto.getRefCodeType());
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/submit")
    public HttpStatus submit(@RequestBody RefCodeTypeMaintenanceDto dto) {
        return refCodeTypeMaintenanceService.save(dto);
    }
}
