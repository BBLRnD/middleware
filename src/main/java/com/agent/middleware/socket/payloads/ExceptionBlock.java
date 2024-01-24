package com.agent.middleware.socket.payloads;

import lombok.Data;

import java.util.List;

@Data
public class ExceptionBlock {

    private List<String> headerInfo;
    private int numOfRecs;
    private String messageToDisplay;
    private List<List<String>> records;

    public List<String> getHeaderInfo() {
        return headerInfo;
    }

    public void setHeaderInfo(List<String> headerInfo) {
        this.headerInfo = headerInfo;
    }

    public int getNumOfRecs() {
        return numOfRecs;
    }

    public void setNumOfRecs(int numOfRecs) {
        this.numOfRecs = numOfRecs;
    }

    public String getMessageToDisplay() {
        return messageToDisplay;
    }

    public void setMessageToDisplay(String messageToDisplay) {
        this.messageToDisplay = messageToDisplay;
    }

    public List<List<String>> getRecords() {
        return records;
    }

    public void setRecords(List<List<String>> records) {
        this.records = records;
    }

}
