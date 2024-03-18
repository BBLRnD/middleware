package com.agent.middleware.controller;

import com.agent.middleware.dto.CriteriaFormDto;
import com.agent.middleware.dto.UserLoginDto;
import com.agent.middleware.service.RefCTypeMaintService;
import com.bbl.util.model.ListBlock;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class RefCTypeMaintController {
    private final RefCTypeMaintService refCTypeMaintService;
    public RefCTypeMaintController(RefCTypeMaintService refCTypeMaintService) {
        this.refCTypeMaintService = refCTypeMaintService;
    }
    //@PreAuthorize("hasRole('USER')")
    @PostMapping("/public/get-refType-list")
    public ListBlock getRefCodeTypeList(@RequestBody CriteriaFormDto criteriaFormDto) {
        System.out.println(criteriaFormDto);
        return refCTypeMaintService.getRefCodeTypeList(criteriaFormDto.getFunCode(),criteriaFormDto.getRefTypeOrDsc());
    }
}
