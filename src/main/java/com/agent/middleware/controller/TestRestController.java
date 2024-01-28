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

import java.util.*;

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
        exceptionBlock.setHeaderInfo(Arrays.asList("ErrorCode", "ErrorMessage", "ErrorType", "Datetime"));
        exceptionBlock.setMessageToDisplay("Whatever message you want");
        exceptionBlock.setNumOfRecs("23");

        List<List<String>> values = new ArrayList<>();

        List<String> value1 = Arrays.asList("000", "SUCCESS", "COMPLETE", "01-01-2023");
        List<String> value2 = Arrays.asList("001", "IN_PROGRESS","WARNING", "25-01-2023");
        List<String> value3 = Arrays.asList("001", "IN_PROGRESS","WARNING", "26-01-2023");
        values.add(value1);
        values.add(value2);
        values.add(value3);
        exceptionBlock.setRecords(values);
        socketRequestPayload.setExceptionBlock(exceptionBlock);
        String jsonString = socketService.socketPayloadObject(socketRequestPayload);
        System.out.println(jsonString);
        SocketPayload socketPayload = mapperUtil.stringToObject(jsonString, SocketPayload.class);
//        Map<String,String> dotValues = mapperUtil.convertToDotNotation(socketPayload);
        HashMap<String, Integer> headerInfo = socketPayload.getExceptionBlock().getHeaderInfo()
                .stream()
                .collect(HashMap<String, Integer>::new,
                        (map, streamValue) -> map.put(streamValue, map.size()),
                        (map, map2) -> {
                        });
        System.out.println(socketPayload.getExceptionBlock().getRecords().get(2).get(headerInfo.get("Datetime")));
//        SocketRequestPayload socketRequestPayload1 = socketService.socketRequestObject()
        return HttpStatus.OK;
    }
}
