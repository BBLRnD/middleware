package com.agent.middleware.dto.socket;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ExceptionBlock{
    private String[] headerInfo;
    private String numOfRecs;
    private String messageToDisplay;
    private List<String[]> records;


    public ExceptionBlock exceptionBlock(String exceptionBlockStr){
        String[] parts = exceptionBlockStr.split("\\[|\\]");
        String[] headerInfo =null;
        String numOfRecs = "0";
        String messageToDisplay = "";
        List<String[]> records = new ArrayList<>();
        for (String part : parts) {
            String[] keyValue = part.split("=");

            if (keyValue.length > 1) {
                String key = keyValue[0];
                String value = keyValue[1];

                switch (key) {
                    case "headerInfo":
                        headerInfo = value.split("\\|");
                        break;
                    case "numOfRecs":
                        if (!value.isEmpty()) {
                            numOfRecs = value;
                        }
                        break;
                    case "messageToDisplay":
                        messageToDisplay = value;
                        break;
                    case "records":
                        String[] recordStrings = value.split("~");
                        for (String recordString : recordStrings) {
                            records.add(recordString.split("\\|"));
                        }
                        break;
                }
            }
        }

        ExceptionBlock exceptionBlock = new ExceptionBlock();
        exceptionBlock.setHeaderInfo(headerInfo);
        exceptionBlock.setNumOfRecs(numOfRecs);
        exceptionBlock.setMessageToDisplay(messageToDisplay);
        exceptionBlock.setRecords(records);
        return exceptionBlock;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[exceptionBlock=");
        result.append("[headerInfo=").append(headerInfo != null ? String.join(" | ", headerInfo) : "").append("]");
        result.append("[numOfRecs=").append(numOfRecs).append("]");
        result.append("[messageToDisplay=").append(messageToDisplay != null ? messageToDisplay : "").append("]");
        result.append("[records=");

        if (records != null && !records.isEmpty()) {
            List<String> recordStrings = new ArrayList<>();
            for (String[] record : records) {
                recordStrings.add(String.join("|",record));
            }
            result.append(String.join(" ~ ", recordStrings));
        }
        result.append("]]");

        return result.toString();
    }
}
