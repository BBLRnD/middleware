package com.agent.middleware.socket;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseSocketStringObject {
    private String callingInfo;
    private String securityInfo;
    private String callingParam;
    private String listBlock;
    private String generalDataBlock;
    private String statusBlock;
    private String exceptionBlock;
    private String mrhBlock;
}
