package com.agent.middleware.service;

import com.agent.middleware.constants.ServiceNameConstant;
import com.agent.middleware.dto.UserSession;
import com.agent.middleware.exception.SocketResponseException;
import com.bbl.servicepool.LimoSocketClient;
import com.bbl.util.deviceInfo.HashGen;
import com.bbl.util.model.*;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.HashMap;

@Service
public class RefCodeTypeMaintenanceServiceImpl implements RefCodeTypeMaintenanceService {
    private final UserSession userSession;
    public RefCodeTypeMaintenanceServiceImpl(UserSession userSession) {
        this.userSession = userSession;
    }
    @SneakyThrows
    @Override
    public ListBlock getRefCodeTypeList(String functionCode,String refTypeOrDsc) {
        SocketPayload socketRequestPayload = SocketPayload.getInstance();
        //1. calling info
        CallingInfo callingInfo = CallingInfo.getInstance();
        callingInfo.setVersionInfo("1.0.0");
        callingInfo.setFuncCode(functionCode);
        callingInfo.setServiceName(ServiceNameConstant.FETCH_REFCODE_TYPE_LIST);
        socketRequestPayload.setCallingInfo(callingInfo);

        HashGen hashGen = HashGen.getInstance();
        DeviceInfo deviceInfo = DeviceInfo.getInstance();
        deviceInfo.setIpAddress(InetAddress.getLocalHost().getHostAddress());
        deviceInfo.setProcessorId("1234");
        deviceInfo.setMacAddress("abcd");
        deviceInfo.setBrowser("chrome");

        deviceInfo.setDeviceHash(hashGen.getDeviceToken(deviceInfo.getIpAddress(),deviceInfo.getProcessorId(),
                deviceInfo.getMacAddress(),deviceInfo.getBrowser()));
        socketRequestPayload.setDeviceInfo(deviceInfo);


        SecurityInfo securityInfo = SecurityInfo.getInstance();
        securityInfo.setSecurityToken(userSession.getSecurityToken());
        securityInfo.setUserId(userSession.getUserId());
        securityInfo.setSaltValue(userSession.getSaltValue());
        securityInfo.setSessionId(userSession.getSessionId());
        socketRequestPayload.setSecurityInfo(securityInfo);


        //3. gen block
        CriteriaBlock criteriaBlock = CriteriaBlock.getInstance();
        HashMap<String, String> criteriaData = new HashMap<>();
        // need to make dynamic
        criteriaData.put("refCodeTypeDesc",refTypeOrDsc);
        criteriaData.put("numOfRecsPerPage","10");
        criteriaData.put("pageNum","1");
        criteriaBlock.setCriteriaData(criteriaData);
        socketRequestPayload.setCriteriaBlock(criteriaBlock);
        String payloadAsString = socketRequestPayload.toString();

        System.out.println(payloadAsString);

        // Socket Communication with app
        LimoSocketClient locLimoSocketClient = new LimoSocketClient();
        String toReceive = locLimoSocketClient.processRequest(payloadAsString);
        System.out.println(toReceive);
        SocketPayload socketPayloadResponse = SocketPayload.getInstance().toObject(toReceive);

        System.out.println(socketPayloadResponse);

        System.out.println(socketPayloadResponse.getStatusBlock().getResponseCode());

        if (socketPayloadResponse.getStatusBlock().
                getResponseCode().equalsIgnoreCase("SUCCESS")) {
            return socketPayloadResponse.getListBlock();
        }else{
            throw new SocketResponseException(socketPayloadResponse.getStatusBlock().getResponseMessage(),
                    socketPayloadResponse.getStatusBlock().getErrorCode());
        }
    }
}
