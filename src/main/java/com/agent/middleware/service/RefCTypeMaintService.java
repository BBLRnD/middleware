package com.agent.middleware.service;

import com.bbl.util.model.ListBlock;

public interface RefCTypeMaintService {

    ListBlock getRefCodeTypeList(String functionCode,String refTypeOrDesc);
}
