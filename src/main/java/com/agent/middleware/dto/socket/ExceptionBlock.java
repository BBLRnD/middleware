package com.agent.middleware.dto.socket;

import lombok.Data;

import java.util.List;

@Data
public class ExceptionBlock implements BaseSocketObject {
    private List<String> headerInfo;
    private String numOfRecs;
    private String messageToDisplay;
    private List<List<String>> records;
}
