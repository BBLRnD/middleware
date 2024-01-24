package com.agent.middleware.dto.socket;

import java.util.List;

public class MrhBlockDetails implements BaseSocketObject {

    private List<String> headerInfo;

    private String numberOfRecs;

    private String messageToDisplay;

    private List<String> rows;
}
