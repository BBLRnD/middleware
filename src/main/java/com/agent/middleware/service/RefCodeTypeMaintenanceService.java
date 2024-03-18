package com.agent.middleware.service;

import com.bbl.util.model.ListBlock;

public interface RefCodeTypeMaintenanceService {

    ListBlock getRefCodeTypeList(String functionCode,String refTypeOrDesc);
}
