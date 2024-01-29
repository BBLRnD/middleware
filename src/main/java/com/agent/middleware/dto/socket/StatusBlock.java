package com.agent.middleware.dto.socket;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StatusBlock {
    private String responseCode;
    private String responseMessage;


    public StatusBlock(String statusStr, SocketPayload socketPayload){
        socketPayload.setStatusBlock(statusBlock(statusStr));
    }

    private StatusBlock statusBlock(String statusStr){

        StatusBlock statusBlock = new StatusBlock();

        String[] keyValuePairs = statusStr.split("\\[|\\||\\]");
        for (String keyValuePair : keyValuePairs) {
            String[] keyValue = keyValuePair.split("=");

            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                switch (key) {
                    case "responseCode":
                        statusBlock.setResponseCode(value);
                        break;
                    case "responseMessage":
                        statusBlock.setResponseMessage(value);
                        break;
                }
            }
        }
        return statusBlock;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[").append("statusBlock=").append("[responseCode=").append(responseCode).append("|");
        result.append("responseMessage=").append(responseMessage).append("]]");
        return result.toString();
    }
}
