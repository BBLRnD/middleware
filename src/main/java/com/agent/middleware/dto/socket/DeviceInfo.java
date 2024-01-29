package com.agent.middleware.dto.socket;


public class DeviceInfo {
    private String ipAddress;
    private String processorId;
    private String macAddress;
    private String browser;

    public DeviceInfo(String deviceStr, SocketPayload socketPayload){
        socketPayload.setDeviceInfo(deviceInfo(deviceStr));
    }

    public DeviceInfo() {
    }

    private DeviceInfo deviceInfo(String deviceStr) {

        DeviceInfo deviceInfo = new DeviceInfo();
        String[] particles = deviceStr.split("\\[|\\]|\\|");

        for (String particle : particles) {
            String[] keyValue = particle.split("=");

            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                if ("ipAddress".equals(key)) {
                    deviceInfo.setIpAddress(value);
                } else if ("processorId".equals(key)) {
                    deviceInfo.setProcessorId(value);
                } else if ("macAddress".equals(key)) {
                    deviceInfo.setMacAddress(value);
                } else if ("browser".equals(key)) {
                    deviceInfo.setBrowser(value);
                }

            }
        }
        return deviceInfo;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[").append("deviceInfo=")
                .append("[ipAddress=").append(ipAddress).append("|")
                .append("processorId=").append(processorId).append("|")
                .append("macAddress=").append(macAddress).append("|")
                .append("browser=").append(browser).append("]]");
        return result.toString();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getProcessorId() {
        return processorId;
    }

    public void setProcessorId(String processorId) {
        this.processorId = processorId;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }
}
