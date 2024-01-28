package com.agent.middleware.controller;


import com.agent.middleware.constants.RegexConstant;
import com.agent.middleware.dto.socket.CallingInfo;
import com.agent.middleware.dto.socket.ExceptionBlock;
import com.agent.middleware.dto.socket.ListBlock;
import com.agent.middleware.dto.socket.SocketPayload;
import com.agent.middleware.dto.socket.*;
import com.agent.middleware.service.SocketService;
import com.agent.middleware.util.MapperUtil;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        CallingParam callingParam = new CallingParam();
        callingParam.callingParam("[criteria1=value1|criteria2=value2|actionCode=Submit|submitWithExceptionOverride=Y]");
        HashMap<String,String> reqParam = new HashMap<>();
        reqParam.put("criteria1","value1");
        reqParam.put("criteria2","value2");
        callingParam.setReqParam(reqParam);
        callingParam.setActionCode("Submit");
        callingParam.setExceptionOverride(true);
        socketRequestPayload.setCallingParam(callingParam);

        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setIpAddress("127.0.0.1");
        deviceInfo.setProcessorId("123");
        deviceInfo.setMacAddress("123");
        deviceInfo.setBrowser("chrome");
        socketRequestPayload.setDeviceInfo(deviceInfo);

        GenBlock genBlock = new GenBlock();
        HashMap<String,String> formData = new HashMap<>();
        formData.put("key1" , "value1");
        formData.put("key2" , "value2");
        genBlock.setFormData(formData);
        socketRequestPayload.setGenBlock(genBlock);

        SecurityInfo securityInfo = new SecurityInfo();
        securityInfo.setUserId("123");
        securityInfo.setSessionId("123");
        securityInfo.setSecurityToken("123");
        securityInfo.setSaltValue("123");
        socketRequestPayload.setSecurityInfo(securityInfo);

        StatusBlock statusBlock = new StatusBlock();
        statusBlock.setResponseCode("SUCCESS");
        statusBlock.setResponseMessage("message");
        socketRequestPayload.setStatusBlock(statusBlock);

        MrhBlock mrhBlock = new MrhBlock();
        mrhBlock.setNumberOfMrh(2);
        List<MrhBlockDetails> mrhBlockDetails = new ArrayList<>();
        MrhBlockDetails mrhBlockDetail = new MrhBlockDetails();
        mrhBlockDetail.setListName("mrhBlockData");
        mrhBlockDetail.setNumberOfRecs(2);
        String[] str = new String[]{"role","desc","group","delFlg","lchgTime"};
        mrhBlockDetail.setHeaderInfo(str);
        mrhBlockDetail.setMessage("Hello");
        mrhBlockDetail.setMaxPageNum(4);
        mrhBlockDetail.setCurPageNum(1);
        List<String[]> dataBlocks = new ArrayList<>();
        String[] value4 = {"CBS", "Core Banking Systems", "Y", "N", "06-01-2024 12:41:20","abc","def"};
        String[] value5 = {"SERDSK", "Service Desk", "N", "N", "06-01-2024 12:41:20","abc","def"};
        String[] value6 = {"DCGRP01", "Data Center", "N", "N", "06-01-2024 12:41:20","33","33"};
        dataBlocks.add(value4);
        dataBlocks.add(value5);
        dataBlocks.add(value6);
        mrhBlockDetail.setDataBlock(dataBlocks);




        // Exception Block
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


        // Generate List Block
        ListBlock listBlock = new ListBlock();
        String[] listHeaders = {"role", "desc", "group", "delFlg","lchgTime"};
        listBlock.setNumberOfRecs(3);
        listBlock.setHeaderInfo(listHeaders);
        listBlock.setMessage("Total 4 pages of 33 records found. Current page 1");
        listBlock.setCurPageNum(1);
        listBlock.setMaxPageNum(4);
        List<String[]> listDataBlock = new ArrayList<>();
        String[] dataBlock1 = {"CBS","Core Banking Systems","Y","N","06-01-2024 12:41:20"};
        String[] dataBlock2 = {"SERDSK","Service Desk","N","N","06-01-2024 12:41:20"};
        String[] dataBlock3 = {"DCGRP01","Data Center","Y","N","06-01-2024 12:41:20"};
        listDataBlock.add(dataBlock1);
        listDataBlock.add(dataBlock2);
        listDataBlock.add(dataBlock3);
        listBlock.setDataBlock(listDataBlock);
        socketRequestPayload.setListBlock(listBlock);

        String payloadAsString = socketRequestPayload.toString();
        System.out.println("REQUEST" + payloadAsString);

        String[] initialSegments = payloadAsString.split("~~");
        CallingInfo callingInfo1;
        ExceptionBlock exceptionBlock1;

        SocketPayload socketPayloadResponse = new SocketPayload();
        for(int i=0; i< initialSegments.length; i++){
            if(initialSegments[i].contains("[callingInfo=")){
                 callingInfo1 = new CallingInfo().callingInfo(initialSegments[i]);
                socketPayloadResponse.setCallingInfo(callingInfo1);
            }else if(initialSegments[i].contains("[deviceInfo=")){
                DeviceInfo deviceInfo1 = new DeviceInfo().deviceInfo(initialSegments[i]);
                socketPayloadResponse.setDeviceInfo(deviceInfo1);
            }else if(initialSegments[i].contains("[callingParam=")){
                exceptionBlock1 = new ExceptionBlock().exceptionBlock(initialSegments[i]);
                socketPayloadResponse.setExceptionBlock(exceptionBlock1);
            }else if(initialSegments[i].contains("[statusBlock=")){
                StatusBlock statusBlock1 = new StatusBlock().statusBlock(initialSegments[i]);
                socketPayloadResponse.setStatusBlock(statusBlock1);
            }
            else if(initialSegments[i].contains("[securityInfo=")){
                SecurityInfo securityInfo1 = new SecurityInfo().securityInfo(initialSegments[i]);
                socketPayloadResponse.setSecurityInfo(securityInfo1);
            }
            else if(initialSegments[i].contains("[genBlock=")){
                GenBlock genBlock1 = new GenBlock().genBlock(initialSegments[i]);
                socketPayloadResponse.setGenBlock(genBlock1);
            }else if(initialSegments[i].contains("[exceptionBlock=")){
                 exceptionBlock1 = new ExceptionBlock().exceptionBlock(initialSegments[i]);
                 socketPayloadResponse.setExceptionBlock(exceptionBlock1);
            }else if(initialSegments[i].contains("[listBlock=")){
                ListBlock listBlock1 = new ListBlock().listBlock(initialSegments[i]);
                socketPayloadResponse.setListBlock(listBlock1);
            }else{
                System.out.println("Segment not found "+ i + " " + initialSegments[i]);
            }
        }
        System.out.println("RESPONSE"+ socketPayloadResponse);
        return HttpStatus.OK;
    }
}
