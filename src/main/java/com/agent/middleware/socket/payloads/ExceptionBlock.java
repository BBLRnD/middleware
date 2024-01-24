package com.agent.middleware.socket.payloads;

import lombok.Data;

import java.util.List;

@Data
public class ExceptionBlock implements BaseSocketObject{

    private List<String> headerInfo;
    private String numOfRecs;
    private String messageToDisplay; ///it will be a list of object
    private List<List<String>> records;

}
