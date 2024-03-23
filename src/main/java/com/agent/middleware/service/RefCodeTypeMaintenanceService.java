package com.agent.middleware.service;

import com.agent.middleware.dto.RefCodeTypeMaintenanceDto;
import com.bbl.util.model.GenDataBlock;
import com.bbl.util.model.ListBlock;
import org.springframework.http.HttpStatus;

public interface RefCodeTypeMaintenanceService {

    ListBlock getRefCodeTypeList(String functionCode,String refTypeOrDesc);
    GenDataBlock getRefCodeTypeDetail(String functionCode, String refCodeType);
    HttpStatus save(RefCodeTypeMaintenanceDto refTypeDto);
}
