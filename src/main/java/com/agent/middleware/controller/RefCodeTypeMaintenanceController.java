package com.agent.middleware.controller;

import com.agent.middleware.dto.CriteriaFormDto;
import com.agent.middleware.service.RefCodeTypeMaintenanceService;
import com.bbl.util.model.ListBlock;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class RefCodeTypeMaintenanceController {
    private final RefCodeTypeMaintenanceService refCodeTypeMaintenanceService;
    public RefCodeTypeMaintenanceController(RefCodeTypeMaintenanceService refCodeTypeMaintenanceService) {
        this.refCodeTypeMaintenanceService = refCodeTypeMaintenanceService;
    }
    //@PreAuthorize("hasRole('USER')")
    @PostMapping("/public/get-refType-list")
    public ListBlock getRefCodeTypeList(@RequestBody CriteriaFormDto criteriaFormDto) {
        System.out.println(criteriaFormDto);
        return refCodeTypeMaintenanceService.getRefCodeTypeList(criteriaFormDto.getFunCode(),criteriaFormDto.getRefTypeOrDsc());
    }
}
