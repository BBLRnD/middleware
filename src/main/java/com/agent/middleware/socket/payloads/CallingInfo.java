package com.agent.middleware.socket.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CallingInfo {
    private String serviceName;
    private String versionInfo;
    private String funcCode;
}
