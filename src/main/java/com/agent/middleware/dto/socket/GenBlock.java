package com.agent.middleware.dto.socket;


import java.util.HashMap;
import java.util.Map;

public class GenBlock {
    public Map<String, String> formData;


    public GenBlock(String genBlockStr, SocketPayload socketPayload){
        socketPayload.setGenBlock(genBlock(genBlockStr));
    }

    public GenBlock() {
    }

    private GenBlock genBlock(String genBlockString) {
        GenBlock genBlock = new GenBlock();
        // genBlockString has the format "genDataBlock=[data1=<>|data2=<>]"
        if (genBlockString.startsWith("[genDataBlock=[")) {
            String genDataBlockString = genBlockString.substring("[genDataBlock=[".length(), genBlockString.length() - 2);
            String[] keyValuePairs = genDataBlockString.split("\\|");

            Map<String, String> genDataMap = new HashMap<>();
            for (String pair : keyValuePairs) {
                String[] parts = pair.split("=");
                if (parts.length == 2) {
                    genDataMap.put(parts[0], parts[1]);
                }
            }
            genBlock.setFormData(genDataMap);
        }
        return genBlock;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[genDataBlock=[");
        for (Map.Entry<String, String> entry : formData.entrySet()) {
            result.append(entry.getKey()).append("=").append(entry.getValue()).append("|");
        }
        if (!formData.isEmpty()) {
            result.deleteCharAt(result.length() - 1);
        }
        result.append("]]");
        return result.toString();
    }

    public Map<String, String> getFormData() {
        return formData;
    }

    public void setFormData(Map<String, String> formData) {
        this.formData = formData;
    }
}
