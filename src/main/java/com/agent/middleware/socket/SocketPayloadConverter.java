package com.agent.middleware.socket;

import com.agent.middleware.socket.payloads.*;

import java.util.HashMap;
import java.util.Map;

public class SocketPayloadConverter {


    private RequestSocketStringObject getRequestStringObject(String socketResponse) {

        RequestSocketStringObject requestSocketStringObject = new RequestSocketStringObject();
        //todo:: breaking into pieces
        return requestSocketStringObject;
    }

    //data:: [serviceName=MoveMoneyService|versionInfo=v01|funcCode=L]
    private CallingInfo getCallingInfoObject(String callingInfoString) {
        CallingInfo callingInfo = new CallingInfo();
        return callingInfo;
    }

    // data:: [criteria1=criteriaValue1|criteria2=criteriaValue2|actionCode=<Submit,Cancel>|submitWithExceptionOverride=Y]
    private CallingParam getCallingParamObject(String callingParamString) {
        CallingParam callingParam = new CallingParam();
        return callingParam;
    }

    // data:: [ipAddress= 127.0.0.1|processorId=coreI7|macAddress=secTok|browser=Mozilla]
    private DeviceInfo getDeviceInfoObject(String deviceInfoString) {
        DeviceInfo deviceInfo = new DeviceInfo();
        return deviceInfo;
    }

    // data:: [headerInfo=abc][numOfRecs=53][messageToDisplay=][pipeSeperatedColumn tilDaSeperatedRow]
    private ExceptionBlock getExceptionBlock(String exceptionBlockString) {
        ExceptionBlock exceptionBlock = new ExceptionBlock();
        return exceptionBlock;
    }


    // [genDataBlock=[data1=<>|data2=<>]]
    public GenBlock getGenBlock(String genBlockString) {

        GenBlock genBlock = new GenBlock();

        // genBlockString has the format "genDataBlock=[data1=<>|data2=<>]"
        if (genBlockString.startsWith("[genDataBlock=[")) {
            String genDataBlockString = genBlockString.substring("[genDataBlock=[".length(), genBlockString.length() - 2);
            String[] keyValuePairs = genDataBlockString.split("\\|");

            Map<String, String> genDataMap = new HashMap<>();

            System.out.println(genDataMap);
            for (String pair : keyValuePairs) {
                String[] parts = pair.split("=");
                if (parts.length == 2) {
                    genDataMap.put(parts[0], parts[1]);
                }
            }
            genBlock.setDataMap(genDataMap);
        }
        // Return the GenBlock object
        return genBlock;
    }

    private ListBlock getListBlock(String listBlockString) {
        ListBlock listBlock = new ListBlock();
        return listBlock;
    }

    private MrhBlock getMrhBlock(String gridMrhBlockString) {
        MrhBlock mrhBlock = new MrhBlock();
        return mrhBlock;
    }

    private SecurityInfo getSecurityInfoObject(String securityString) {
        SecurityInfo securityInfo = new SecurityInfo();
        return securityInfo;
    }


    private StatusBlock getStatusBlock(String statusBlockString) {
        StatusBlock statusBlock = new StatusBlock();

        //todo: segregation logic
        return statusBlock;
    }


    /////////////////////////

    private String getCallingInfoString(CallingInfo callingInfo) {
        return "";
    }

    private String getSecurityInfo(SecurityInfo securityInfo) {
        return "";
    }


    private String getDeviceInfo(DeviceInfo deviceInfo) {
        return "";
    }


    private String getCallingParam(CallingParam callingParam) {
        return "";
    }


    private String getGenBlock(GenBlock genBlock) {
        return genBlock.genBlockToString();
    }

    private String getMrhBlock(MrhBlock mrhBlock) {
        return "";
    }

    private String getListBlock(ListBlock listBlock) {
        return "";
    }

    private String getStatusBlock(StatusBlock statusBlock) {
        return "";
    }

    private String getExceptionBlock(ExceptionBlock exceptionBlock) {
        return "";
    }


}
