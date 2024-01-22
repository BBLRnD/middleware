package com.agent.middleware.socket.payloads;

import lombok.Data;

@Data
public class ExceptionBlock {

    private String headerInfo;
    private String numberOfRecords;
    private String messageToDisplay; ///it will be a list of object

}
