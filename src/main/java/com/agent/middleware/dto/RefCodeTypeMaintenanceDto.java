package com.agent.middleware.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefCodeTypeMaintenanceDto {
    private String functionCode;
    private String refCodeType;
    private String newRefCodeType;
    private String refCodeTypeDesc;
    private String lchgTime;
    private String dependentFlg;
    private String dependentRefCodeType;
    private String depRefCodeTypeDesc;
    private String refCodeLength;
    private String menuId;
}
