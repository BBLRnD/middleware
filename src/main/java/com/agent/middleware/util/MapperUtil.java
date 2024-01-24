package com.agent.middleware.util;

import com.agent.middleware.socket.payloads.BaseSocketObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class MapperUtil {

    private final ObjectMapper objectMapper;

    public MapperUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public <O extends BaseSocketObject>String objectToJson(O obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    public <T extends BaseSocketObject>T stringToObject(String json, Class<T> valueType) throws JsonProcessingException {
        return objectMapper.readValue(json, valueType);
    }
}
