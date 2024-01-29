package com.agent.middleware.dto.socket;

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

    public SocketPayload() {
    }

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

    public CallingInfo getCallingInfo() {
        return callingInfo;
    }

    public void setCallingInfo(CallingInfo callingInfo) {
        this.callingInfo = callingInfo;
    }

    public SecurityInfo getSecurityInfo() {
        return securityInfo;
    }

    public void setSecurityInfo(SecurityInfo securityInfo) {
        this.securityInfo = securityInfo;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public CallingParam getCallingParam() {
        return callingParam;
    }

    public void setCallingParam(CallingParam callingParam) {
        this.callingParam = callingParam;
    }

    public StatusBlock getStatusBlock() {
        return statusBlock;
    }

    public void setStatusBlock(StatusBlock statusBlock) {
        this.statusBlock = statusBlock;
    }

    public ExceptionBlock getExceptionBlock() {
        return exceptionBlock;
    }

    public void setExceptionBlock(ExceptionBlock exceptionBlock) {
        this.exceptionBlock = exceptionBlock;
    }

    public ListBlock getListBlock() {
        return listBlock;
    }

    public void setListBlock(ListBlock listBlock) {
        this.listBlock = listBlock;
    }

    public GenBlock getGenBlock() {
        return genBlock;
    }

    public void setGenBlock(GenBlock genBlock) {
        this.genBlock = genBlock;
    }

    public MrhBlock getMrhBlock() {
        return mrhBlock;
    }

    public void setMrhBlock(MrhBlock mrhBlock) {
        this.mrhBlock = mrhBlock;
    }
}
