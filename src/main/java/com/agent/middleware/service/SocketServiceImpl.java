package com.agent.middleware.service;

import com.agent.middleware.socket.payloads.SocketPayload;
import com.agent.middleware.util.MapperUtil;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class SocketServiceImpl implements SocketService{

    private final MapperUtil mapperUtil;

    public SocketServiceImpl(MapperUtil mapperUtil) {
        this.mapperUtil = mapperUtil;
    }

    @Override
    @SneakyThrows
    public String socketPayloadObject(SocketPayload socketRequestPayload) {
        String socketString = mapperUtil.objectToJson(socketRequestPayload);
        System.out.println(socketString);
        return socketString;
    }



}



