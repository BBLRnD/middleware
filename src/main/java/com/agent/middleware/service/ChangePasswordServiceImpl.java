package com.agent.middleware.service;

import com.agent.middleware.constants.ServiceNameConstant;
import com.agent.middleware.dto.ChangePasswordDto;
import com.agent.middleware.dto.ChangePasswordResponseDto;
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
public class ChangePasswordServiceImpl implements ChangePasswordService {

private final UserSession userSession;
    public ChangePasswordServiceImpl(UserSession userSession) {
        this.userSession = userSession;
    }

    @Override
    public boolean validateChangePasswordDto(ChangePasswordDto changePasswordDto) {
        return
                changePasswordDto.getLoginKeyOld() != null && !changePasswordDto.getLoginKeyOld().isEmpty() &&
                changePasswordDto.getLoginKeyNew() != null && !changePasswordDto.getLoginKeyNew().isEmpty() &&
                changePasswordDto.getLoginKeyRe() != null && !changePasswordDto.getLoginKeyRe().isEmpty() &&
                changePasswordDto.getLoginKeyNew().equals(changePasswordDto.getLoginKeyRe()) &&
                        !changePasswordDto.getLoginKeyOld().equals(changePasswordDto.getLoginKeyNew());
    }


    @SneakyThrows
    @Override
    public ChangePasswordResponseDto savePassword(ChangePasswordDto changePasswordDto) {
        SocketPayload socketRequestPayload = SocketPayload.getInstance();
        //1. calling info
        CallingInfo callingInfo = CallingInfo.getInstance();
        callingInfo.setVersionInfo("1.0.0");
        callingInfo.setFuncCode("M");
        callingInfo.setServiceName(ServiceNameConstant.UPDATE_PASSWORD);
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
        GenDataBlock genDataBlock = GenDataBlock.getInstance();
        HashMap<String, String> formData = new HashMap<>();
        // need to make dynamic
        formData.put("loginId",userSession.getUsername());
        formData.put("loginKeyOld", changePasswordDto.getLoginKeyOld());
        formData.put("loginKeyNew", changePasswordDto.getLoginKeyNew());
        formData.put("loginKeyRe", changePasswordDto.getLoginKeyRe());
        genDataBlock.setFormData(formData);
        socketRequestPayload.setGenDataBlock(genDataBlock);

        String payloadAsString = socketRequestPayload.toString();

        System.out.println(payloadAsString);

        // Socket Communication with app
        LimoSocketClient locLimoSocketClient = new LimoSocketClient();
        String toReceive = locLimoSocketClient.processRequest(payloadAsString);
        SocketPayload socketPayloadResponse = SocketPayload.getInstance().toObject(toReceive);

        System.out.println(socketPayloadResponse.getStatusBlock().getResponseCode());
        if (socketPayloadResponse.getStatusBlock().
                getResponseCode().equalsIgnoreCase("SUCCESS")) {
            ChangePasswordResponseDto changePasswordResponseDto = new ChangePasswordResponseDto();
            changePasswordResponseDto.setMessage(socketPayloadResponse.getStatusBlock().getResponseMessage());
            return changePasswordResponseDto;
        }else{
            throw new SocketResponseException(socketPayloadResponse.getStatusBlock().getResponseMessage(),
                    socketPayloadResponse.getStatusBlock().getErrorCode());
        }
    }
}
