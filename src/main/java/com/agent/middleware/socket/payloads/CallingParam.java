package com.agent.middleware.socket.payloads;

import lombok.Data;

import java.util.HashMap;

@Data
public class CallingParam {

    private HashMap<String, String> hashMap;
    private String actionCode;
    private Boolean submitWithExceptionOverride; // Y/N

    public HashMap<String, String> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<String, String> hashMap) {
        this.hashMap = hashMap;
    }


    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public Boolean getSubmitWithExceptionOverride() {
        return submitWithExceptionOverride;
    }

    public void setSubmitWithExceptionOverride(Boolean submitWithExceptionOverride) {
        this.submitWithExceptionOverride = submitWithExceptionOverride;
    }


}
