package com.agent.middleware.controller;

import com.agent.middleware.dto.coreconfig.RefTypeMaintenanceDto;
import com.agent.middleware.service.RefCodeTypeMaintenanceService;
import com.bbl.util.model.GenDataBlock;
import com.bbl.util.model.ListBlock;
import com.bbl.util.model.StatusBlock;
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
    public ListBlock getRefTypeList(@RequestBody RefTypeMaintenanceDto refTypeMaintenanceDto) {
        return refCodeTypeMaintenanceService.getRefTypeList(refTypeMaintenanceDto);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/get-refType-detail")
    public GenDataBlock getRefTypeDetail(@RequestBody RefTypeMaintenanceDto dto) {
        return refCodeTypeMaintenanceService.getRefCodeTypeDetail(dto.getFunctionCode(), dto.getRefCodeType());
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/submit")
    public StatusBlock submit(@RequestBody RefTypeMaintenanceDto dto) {
        return refCodeTypeMaintenanceService.save(dto);
    }
}
