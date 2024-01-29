package com.agent.middleware.dto.socket;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class CallingParam{
    private HashMap<String, String> reqParam;
    private String actionCode;
    private Boolean exceptionOverride; // Y/N


    public CallingParam(String callingParamStr, SocketPayload socketPayload){
        socketPayload.setCallingParam(callingParam(callingParamStr));
    }

    private CallingParam callingParam(String callingParamStr){

        CallingParam callingParam = new CallingParam();

        String[] keyValuePairs = callingParamStr
                .split("[\\[\\]|]");

        HashMap<String, String> paramMap = new HashMap<>();

        for (String pair : keyValuePairs) {
            String[] keyValue = pair.split("=");

            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                if ("actionCode".equals(key)) {
                    callingParam.setActionCode(value);
                } else if ("submitWithExceptionOverride".equals(key)) {
                    callingParam.setExceptionOverride("Y".equalsIgnoreCase(value));
                } else {
                    paramMap.put(key, value);
                }
            }
        }
        callingParam.setReqParam(paramMap);
        return callingParam;
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[callingParam=[");
        HashMap<String, String> paramMap = reqParam;
        if (paramMap != null && !paramMap.isEmpty()) {
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                result.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("|");
            }
        }

        if (actionCode != null) {
            result.append("actionCode=")
                    .append(actionCode)
                    .append("|");
        }

        if (exceptionOverride != null) {
            result.append("exceptionOverride=")
                    .append(exceptionOverride ? "Y" : "N")
                    .append("]");
        }
        result.append("]");
        return result.toString();
    }
}
