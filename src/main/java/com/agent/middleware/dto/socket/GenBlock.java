package com.agent.middleware.dto.socket;

import java.util.Map;


public class GenBlock implements BaseSocketObject {

    public Map<String, String> genDataMap;

    public void setDataMap(Map<String, String> genDataMap) {
        this.genDataMap = genDataMap;
    }

    public Map<String, String> getDataMap() {
        return genDataMap;
    }

    public String genBlockToString() {
        StringBuilder result = new StringBuilder("[genDataBlock=[");
        Map<String, String> genDataMap = getDataMap();
        for (Map.Entry<String, String> entry : genDataMap.entrySet()) {
            result.append(entry.getKey()).append("=").append(entry.getValue()).append("|");
        }
        if (!genDataMap.isEmpty()) {
            result.deleteCharAt(result.length() - 1);
        }
        result.append("]]");
        return result.toString();
    }
}
