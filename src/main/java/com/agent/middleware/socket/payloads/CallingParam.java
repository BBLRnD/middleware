package com.agent.middleware.socket.payloads;

import lombok.Data;

import java.util.HashMap;

@Data
public class CallingParam {

    private HashMap<String, String> hashMap;

    private String actionCode;
    private Boolean submitWithExceptionOverride; // Y/N
}
