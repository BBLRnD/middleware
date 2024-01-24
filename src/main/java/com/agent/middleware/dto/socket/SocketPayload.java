package com.agent.middleware.dto.socket;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SocketPayload implements BaseSocketObject {
    private CallingInfo callingInfo;
    private SecurityInfo securityInfo;
    private DeviceInfo deviceInfo;
    private CallingParam callingParam;
    private StatusBlock statusBlock;
    private ExceptionBlock exceptionBlock;
    private ListBlock listBlock;
    private GenBlock genBlock;
    private MrhBlock mrhBlock;
}
