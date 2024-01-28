package com.agent.middleware.dto.socket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeviceInfo {
    private String ipAddress;
    private String processorId;
    private String macAddress;
    private String browser;


    public DeviceInfo deviceInfo(String deviceStr) {

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
}
