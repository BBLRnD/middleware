package com.agent.middleware.socket.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MrhBlockDetails implements BaseSocketObject{

    private String listName;
    private Integer numberOfRecs;
    private String[] headerInfo;
    private String message;
    private Integer curPageNum;
    private Integer maxPageNum;
    private List<String[]> dataBlock;
}
