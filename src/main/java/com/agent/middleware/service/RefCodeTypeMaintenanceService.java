package com.agent.middleware.service;

import com.agent.middleware.dto.RefCodeTypeMaintenanceDto;
import com.bbl.util.model.GenDataBlock;
import com.bbl.util.model.ListBlock;
import com.bbl.util.model.StatusBlock;

public interface RefCodeTypeMaintenanceService {

    ListBlock getRefCodeTypeList(String functionCode,String refTypeOrDesc);
    GenDataBlock getRefCodeTypeDetail(String functionCode, String refCodeType);
    StatusBlock save(RefCodeTypeMaintenanceDto refTypeDto);
}
