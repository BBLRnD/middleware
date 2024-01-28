package com.agent.middleware.controller;


import com.agent.middleware.dto.socket.CallingInfo;
import com.agent.middleware.dto.socket.ExceptionBlock;
import com.agent.middleware.dto.socket.SocketPayload;
import com.agent.middleware.service.SocketService;
import com.agent.middleware.util.MapperUtil;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
        String[] headerInfo = {"ErrorCode", "ErrorMessage", "ErrorType", "Datetime"};
        exceptionBlock.setHeaderInfo(headerInfo);
        exceptionBlock.setMessageToDisplay("Whatever message you want");
        exceptionBlock.setNumOfRecs("23");

        List<String[]> values = new ArrayList<>();

        String[] value1 = {"000", "SUCCESS", "COMPLETE", "01-01-2023"};
        String[] value2 = {"001", "IN_PROGRESS", "WARNING", "25-01-2023"};
        String[] value3 = {"001", "IN_PROGRESS", "WARNING", "26-01-2023"};
        values.add(value1);
        values.add(value2);
        values.add(value3);
        exceptionBlock.setRecords(values);
        socketRequestPayload.setExceptionBlock(exceptionBlock);
        String payloadAsString = socketRequestPayload.toString();
        System.out.println(payloadAsString);


        return HttpStatus.OK;
    }
}
