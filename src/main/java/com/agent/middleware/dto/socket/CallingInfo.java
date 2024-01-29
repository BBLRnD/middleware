package com.agent.middleware.dto.socket;

public class CallingInfo {
    private String serviceName;
    private String versionInfo;
    private String funcCode;

    public CallingInfo(String callingInfoStr, SocketPayload socketPayload) {
        socketPayload.setCallingInfo(callingInfo(callingInfoStr));
    }

    public CallingInfo() {

    }

    private CallingInfo callingInfo(String callingInfoStr) {
        CallingInfo callingInfo = new CallingInfo();
        String[] particles = callingInfoStr
                .split("\\[|\\]|\\|");
        for (String particle : particles) {
            String[] keyValue = particle.split("=");

            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                if ("serviceName".equals(key)) {
                    callingInfo.setServiceName(value);
                } else if ("versionInfo".equals(key)) {
                    callingInfo.setVersionInfo(value);
                } else if ("funcCode".equals(key)) {
                    callingInfo.setFuncCode(value);
                }
            }
        }

        return callingInfo;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[").append("callingInfo=").append("[serviceName=").append(serviceName).append("|");
        result.append("versionInfo=").append(versionInfo).append("|");
        result.append("funcCode=").append(funcCode).append("]]");
        return result.toString();
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(String versionInfo) {
        this.versionInfo = versionInfo;
    }

    public String getFuncCode() {
        return funcCode;
    }

    public void setFuncCode(String funcCode) {
        this.funcCode = funcCode;
    }
}
