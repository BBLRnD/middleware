package com.agent.middleware.socket;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestSocketStringObject {
    private String callingInfo;
    private String securityInfo;
    private String deviceInfo;
    private String callingParam;
    private String genDataBlock;
    private String mrhBlock;
}
