package com.agent.middleware.controller;


import com.agent.middleware.dto.socket.*;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TestRestController {

    @SneakyThrows
    @PostMapping("/public/test")
    public HttpStatus test() {
        Long startTime = new Date().getTime();
        SocketPayload socketRequestPayload = new SocketPayload();

        //1. calling info
        CallingInfo callingInfo = new CallingInfo();
        callingInfo.setVersionInfo("v1");
        callingInfo.setFuncCode("helloWorld");
        callingInfo.setServiceName("menuService");
        socketRequestPayload.setCallingInfo(callingInfo);

        //2. calling param
        CallingParam callingParam = new CallingParam();
        HashMap<String, String> reqParam = new HashMap<>();
        reqParam.put("criteria1", "value1");
        reqParam.put("criteria2", "value2");
        callingParam.setReqParam(reqParam);
        callingParam.setActionCode("Submit");
        callingParam.setExceptionOverride(true);
        socketRequestPayload.setCallingParam(callingParam);

        //3. device info
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setIpAddress("127.0.0.1");
        deviceInfo.setProcessorId("123");
        deviceInfo.setMacAddress("123");
        deviceInfo.setBrowser("chrome");
        socketRequestPayload.setDeviceInfo(deviceInfo);

        // 4. exception Block
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

        //5. gen block
        GenBlock genBlock = new GenBlock();
        HashMap<String, String> formData = new HashMap<>();
        formData.put("key1", "value1");
        formData.put("key2", "value2");
        genBlock.setFormData(formData);
        socketRequestPayload.setGenBlock(genBlock);

        // 6. list block
        ListBlock listBlock = new ListBlock();
        listBlock.setNumberOfRecs(3);
        listBlock.setCurPageNum(1);
        listBlock.setMaxPageNum(4);
        String[] listHeaders = {"role", "desc", "group", "delFlg", "lchgTime"};
        listBlock.setHeaderInfo(listHeaders);
        listBlock.setMessage("Total 4 pages of 33 records found. Current page 1");
        List<String[]> listDataBlock = new ArrayList<>();
        String[] dataBlock1 = {"CBS", "Core Banking Systems", "Y", "N", "06-01-2024 12:41:20"};
        String[] dataBlock2 = {"SERDSK", "Service Desk", "N", "N", "06-01-2024 12:41:20"};
        String[] dataBlock3 = {"DCGRP01", "Data Center", "Y", "N", "06-01-2024 12:41:20"};
        listDataBlock.add(dataBlock1);
        listDataBlock.add(dataBlock2);
        listDataBlock.add(dataBlock3);
        listBlock.setDataBlock(listDataBlock);
        socketRequestPayload.setListBlock(listBlock);

        //7. mrh block
        MrhBlock mrhBlock = new MrhBlock();
        mrhBlock.setNumberOfMrh(2);

        List<MrhBlockDetails> mrhBlockDetailsList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            MrhBlockDetails mrhBlockDetail = new MrhBlockDetails();
            mrhBlockDetail.setNumberOfRecs(3);
            mrhBlockDetail.setListName("mrhBlockData1");
            String[] str = new String[]{"role", "desc", "group", "delFlg", "lchgTime"};
            mrhBlockDetail.setHeaderInfo(str);
            mrhBlockDetail.setMessage("Hello");
            mrhBlockDetail.setMaxPageNum(4);
            mrhBlockDetail.setCurPageNum(1);
            List<String[]> dataBlocks = new ArrayList<>();
            String[] value4 = {"CBS", "Core Banking Systems", "Y", "N", "06-01-2024 12:41:20", "abc", "def"};
            String[] value5 = {"SERDSK", "Service Desk", "N", "N", "06-01-2024 12:41:20", "abc", "def"};
            String[] value6 = {"DCGRP01", "Data Center", "N", "N", "06-01-2024 12:41:20", "33", "33"};
            dataBlocks.add(value4);
            dataBlocks.add(value5);
            dataBlocks.add(value6);
            mrhBlockDetail.setDataBlock(dataBlocks);
            mrhBlockDetailsList.add(mrhBlockDetail);
        }
        mrhBlock.setMrhBlocks(mrhBlockDetailsList);
        socketRequestPayload.setMrhBlock(mrhBlock);

        //8. security block
        SecurityInfo securityInfo = new SecurityInfo();
        securityInfo.setUserId("123");
        securityInfo.setSessionId("123");
        securityInfo.setSecurityToken("123");
        securityInfo.setSaltValue("123");
        socketRequestPayload.setSecurityInfo(securityInfo);

        //9. status block
        StatusBlock statusBlock = new StatusBlock();
        statusBlock.setResponseCode("SUCCESS");
        statusBlock.setResponseMessage("message");
        socketRequestPayload.setStatusBlock(statusBlock);


        // object to string
        String payloadAsString = socketRequestPayload.toString();
        System.out.println("REQUEST" + payloadAsString);
        String[] initialSegments = payloadAsString.split("~~");

        // string to object
        SocketPayload socketPayloadResponse = new SocketPayload();
        for (int i = 0; i < initialSegments.length; i++) {
            if (initialSegments[i].contains("[callingInfo=")) {
                new CallingInfo(initialSegments[i], socketPayloadResponse);
            } else if (initialSegments[i].contains("[callingParam=")) {
                new CallingParam(initialSegments[i], socketPayloadResponse);
            } else if (initialSegments[i].contains("[deviceInfo=")) {
                new DeviceInfo(initialSegments[i], socketPayloadResponse);
            } else if (initialSegments[i].contains("[exceptionBlock=")) {
                new ExceptionBlock(initialSegments[i], socketPayloadResponse);
            } else if (initialSegments[i].contains("[genDataBlock=")) {
                new GenBlock(initialSegments[i], socketPayloadResponse);
            } else if (initialSegments[i].contains("[listBlock=")) {
                new ListBlock(initialSegments[i], socketPayloadResponse);
            } else if (initialSegments[i].contains("[mrhBlock=")) {
                new MrhBlock(initialSegments[i], socketPayloadResponse);
            } else if (initialSegments[i].contains("[securityInfo=")) {
                new SecurityInfo(initialSegments[i], socketPayloadResponse);
            } else if (initialSegments[i].contains("[statusBlock=")) {
                new StatusBlock(initialSegments[i], socketPayloadResponse);
            } else {
                System.out.println("Segment not found " + i + " " + initialSegments[i]);
            }
        }
        System.out.println("RESPONSE" + socketPayloadResponse);
        System.out.println("Time Took :" + (new Date().getTime() - startTime));
        return HttpStatus.OK;
    }
}
