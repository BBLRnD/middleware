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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(callingInfo == null ? "" : callingInfo.toString())
                .append(callingInfo == null ? "" : "~~")
                .append(securityInfo == null ? "" : securityInfo.toString())
                .append(securityInfo == null ? "" : "~~")
                .append(deviceInfo == null ? "" : deviceInfo.toString())
                .append(deviceInfo == null ? "" : "~~")
                .append(callingParam == null ? "" : callingParam.toString())
                .append(callingParam == null ? "" : "~~")
                .append(statusBlock == null ? "" : statusBlock.toString())
                .append(statusBlock == null ? "" : "~~")
                .append(exceptionBlock == null ? "" : exceptionBlock.toString())
                .append(exceptionBlock == null ? "" : "~~")
                .append(listBlock == null ? "" : listBlock.toString())
                .append(listBlock == null ? "" : "~~")
                .append(genBlock == null ? "" : genBlock.toString())
                .append(genBlock == null ? "" : "~~")
                .append(mrhBlock == null ? "" : mrhBlock.toString());
        return stringBuilder.toString();
    }
}
