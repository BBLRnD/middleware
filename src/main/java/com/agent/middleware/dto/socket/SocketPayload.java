package com.agent.middleware.dto.socket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SocketPayload {
    private CallingInfo callingInfo;
    private SecurityInfo securityInfo;
    private DeviceInfo deviceInfo;
    private CallingParam callingParam;
    private StatusBlock statusBlock;
    private ExceptionBlock exceptionBlock;
    private ListBlock listBlock;
    private GenBlock genBlock;
    private MrhBlock mrhBlock;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");
        stringBuilder.append(callingInfo.toString())
                .append(securityInfo == null ? "" : securityInfo.toString())
                .append(deviceInfo == null ? "" : deviceInfo.toString())
                .append(callingParam == null ? "" : callingParam.toString())
                .append(statusBlock == null ? "" : statusBlock.toString())
                .append(exceptionBlock == null ? "" : exceptionBlock.toString())
                .append(listBlock == null ? "" : listBlock.toString())
                .append(genBlock == null ? "" : genBlock.toString())
                .append(mrhBlock == null ? "" : mrhBlock.toString())
                .append("\\]");
        return stringBuilder.toString();
    }
}
