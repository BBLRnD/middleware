package com.agent.middleware.controller;


import com.agent.middleware.service.SocketService;
import com.agent.middleware.socket.payloads.CallingInfo;
import com.agent.middleware.socket.payloads.ExceptionBlock;
import com.agent.middleware.socket.payloads.SocketPayload;
import com.agent.middleware.util.MapperUtil;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TestRestController {

    private final SocketService socketService;

    private final MapperUtil mapperUtil;

    public TestRestController(SocketService socketService, MapperUtil mapperUtil) {
        this.socketService = socketService;
        this.mapperUtil = mapperUtil;
    }


    @SneakyThrows
    @PostMapping("/public/test")
    public HttpStatus test() {
        SocketPayload socketRequestPayload = new SocketPayload();
        CallingInfo callingInfo = new CallingInfo();
        callingInfo.setVersionInfo("v1");
        callingInfo.setFuncCode("helloWorld");
        callingInfo.setServiceName("menuService");
        socketRequestPayload.setCallingInfo(callingInfo);

        ExceptionBlock exceptionBlock = new ExceptionBlock();
        exceptionBlock.setHeaderInfo(Arrays.asList("errorCode", "errorMessage"));
        exceptionBlock.setMessageToDisplay("Whatever message you want");
        exceptionBlock.setNumOfRecs("23");

        List<List<String>> values = new ArrayList<>();

        List<String> value1 = Arrays.asList("000", "SUCCESS");
        List<String> value2 = Arrays.asList("001", "IN_PROGRESS");
        values.add(value1);
        values.add(value2);
        exceptionBlock.setRecords(values);
        socketRequestPayload.setExceptionBlock(exceptionBlock);
        String jsonString = socketService.socketPayloadObject(socketRequestPayload);
        System.out.println(jsonString);
        SocketPayload socketPayload = mapperUtil.stringToObject(jsonString, SocketPayload.class);
//        SocketRequestPayload socketRequestPayload1 = socketService.socketRequestObject()
        return HttpStatus.OK;
    }
}
