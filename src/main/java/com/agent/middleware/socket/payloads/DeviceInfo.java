package com.agent.middleware.socket.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeviceInfo {
    private String ipAddress;
    private String processorId;
    private String macAddress;
    private String browser;
}
