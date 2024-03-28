package com.agent.middleware.service;

import com.agent.middleware.dto.coreconfig.RefTypeMaintenanceDto;
import com.bbl.util.model.GenDataBlock;
import com.bbl.util.model.ListBlock;
import com.bbl.util.model.StatusBlock;

public interface RefCodeTypeMaintenanceService {

    ListBlock getRefTypeList(RefTypeMaintenanceDto refTypeMaintenanceDto);
    GenDataBlock getRefCodeTypeDetail(String functionCode, String refCodeType);
    StatusBlock save(RefTypeMaintenanceDto refTypeDto);
}
