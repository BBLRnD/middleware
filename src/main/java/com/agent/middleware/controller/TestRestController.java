package com.agent.middleware.controller;


import com.agent.middleware.constants.ServiceNameConstant;
import com.bbl.servicepool.LimoSocketClient;
import com.bbl.util.model.CallingInfo;
import com.bbl.util.model.GenDataBlock;
import com.bbl.util.model.SocketPayload;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1")
public class TestRestController {
    public TestRestController() {

    }

    @SneakyThrows
    @PostMapping("/public/test")
    public String test() {
        Long startTime = new Date().getTime();
        SocketPayload socketRequestPayload = SocketPayload.getInstance();

        //1. calling info
        CallingInfo callingInfo = CallingInfo.getInstance();
        callingInfo.setVersionInfo("1.0.0");
        callingInfo.setFuncCode("M");
        callingInfo.setServiceName(ServiceNameConstant.LOGIN);
        socketRequestPayload.setCallingInfo(callingInfo);


        //5. gen block
        GenDataBlock genDataBlock = GenDataBlock.getInstance();
        HashMap<String, String> formData = new HashMap<>();
        formData.put("loginId", "ADMIN1");
        formData.put("loginKey", "admin");
        genDataBlock.setFormData(formData);
        socketRequestPayload.setGenDataBlock(genDataBlock);

        // object to string
        String payloadAsString = socketRequestPayload.toString();
        System.out.println("REQUEST" + payloadAsString);


        LimoSocketClient locLimoSocketClient = new LimoSocketClient();
        String toReceive = locLimoSocketClient.processRequest(payloadAsString);
        System.out.println("Response " + toReceive);

        // string to object
        SocketPayload socketPayloadResponse = SocketPayload.getInstance().toObject(toReceive);
        System.out.println("Time Took :" + (new Date().getTime() - startTime));
        return socketPayloadResponse.getStatusBlock().getResponseMessage();
    }
}
