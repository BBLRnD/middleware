package com.agent.middleware.dto.socket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SecurityInfo {
    private String userId;
    private String sessionId;
    private String securityToken;
    private String saltValue;

    public SecurityInfo securityInfo(String securityStr) {
        SecurityInfo securityInfo = new SecurityInfo();
        String[] keyValuePairs = securityStr.split("\\[|\\||\\]");
        for (String keyValuePair : keyValuePairs) {
            String[] keyValue = keyValuePair.split("=");

            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                switch (key) {
                    case "userId":
                        securityInfo.setUserId(value);
                        break;
                    case "sessionId":
                        securityInfo.setSessionId(value);
                        break;
                    case "securityToken":
                        securityInfo.setSecurityToken(value);
                        break;
                    case "saltValue":
                        securityInfo.setSaltValue(value);
                        break;
                }
            }
        }
        return securityInfo;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[").append("securityInfo=").append("[userId=").append(userId).append("|");
        result.append("sessionId=").append(sessionId).append("|");
        result.append("securityToken=").append(securityToken).append("|");
        result.append("saltValue=").append(saltValue).append("]]");
        return result.toString();
    }
}
