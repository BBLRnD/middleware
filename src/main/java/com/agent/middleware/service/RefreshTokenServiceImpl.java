package com.agent.middleware.service;

import com.agent.middleware.constants.ServiceNameConstant;
import com.agent.middleware.dto.RefreshTokenRequestDto;
import com.agent.middleware.dto.UserSession;
import com.agent.middleware.entity.RefreshToken;
import com.bbl.servicepool.LimoSocketClient;
import com.bbl.util.deviceInfo.HashGen;
import com.bbl.util.model.*;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Log4j2
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${refresh.token.validity}")
    private long REFRESH_TOKEN_VALIDITY;
    private final UserSession userSession;

//    private final UserService userService;

    public RefreshTokenServiceImpl(UserSession userSession) {
        this.userSession = userSession;
    }

    public RefreshToken createRefreshToken(String username) {
//        UserInfo userInfo = (UserInfo) userService.loadUserByUsername(username);
        return RefreshToken.builder()
//                .userId(userInfo.getId())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(REFRESH_TOKEN_VALIDITY))
                .build();
    }


    @SneakyThrows
    public RefreshToken findByToken(String token) {
        CallingInfo callingInfo = CallingInfo.getInstance();
        callingInfo.setVersionInfo("1.0.0");
        callingInfo.setFuncCode("M");
        callingInfo.setFuncCode("GetRefreshToken");
        SocketPayload socketPayload = SocketPayload.getInstance();

        HashGen hashGen = HashGen.getInstance();
        DeviceInfo deviceInfo = DeviceInfo.getInstance();
        deviceInfo.setIpAddress(InetAddress.getLocalHost().getHostAddress());
        deviceInfo.setProcessorId("1234");
        deviceInfo.setMacAddress("abcd");
        deviceInfo.setBrowser("chrome");

        deviceInfo.setDeviceHash(hashGen.getDeviceToken(deviceInfo.getIpAddress(), deviceInfo.getProcessorId(),
                deviceInfo.getMacAddress(), deviceInfo.getBrowser()));
        socketPayload.setDeviceInfo(deviceInfo);
        GenDataBlock genDataBlock = GenDataBlock.getInstance();
        Map<String, String> formData = new HashMap<>();
        formData.put("refreshToken", token);
        genDataBlock.setFormData(formData);
        socketPayload.setGenDataBlock(genDataBlock);
        LimoSocketClient locLimoSocketClient = new LimoSocketClient();
        String toReceive = locLimoSocketClient.processRequest(socketPayload.toString());
        SocketPayload socketPayloadResponse = SocketPayload.getInstance().toObject(toReceive);

        return new RefreshToken();
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }

    @SneakyThrows
    public void deleteRefreshToken(RefreshTokenRequestDto refreshToken) {
        SocketPayload socketRequestPayload = SocketPayload.getInstance();
        //1. calling info
        CallingInfo callingInfo = CallingInfo.getInstance();
        callingInfo.setVersionInfo("1.0.0");
        callingInfo.setFuncCode("M");
        callingInfo.setServiceName(ServiceNameConstant.LOGOUT);
        socketRequestPayload.setCallingInfo(callingInfo);

        // device info
        HashGen hashGen = HashGen.getInstance();
        DeviceInfo deviceInfo = DeviceInfo.getInstance();
        deviceInfo.setIpAddress(InetAddress.getLocalHost().getHostAddress());
        deviceInfo.setProcessorId("1234");
        deviceInfo.setMacAddress("abcd");
        deviceInfo.setBrowser("chrome");

        deviceInfo.setDeviceHash(hashGen.getDeviceToken(deviceInfo.getIpAddress(), deviceInfo.getProcessorId(),
                deviceInfo.getMacAddress(), deviceInfo.getBrowser()));
        socketRequestPayload.setDeviceInfo(deviceInfo);


        GenDataBlock genDataBlock = GenDataBlock.getInstance();
        Map formData = new HashMap<String, String>();
        formData.put("loginId", userSession.getUsername());
        genDataBlock.setFormData(formData);
        socketRequestPayload.setGenDataBlock(genDataBlock);
        //3. security info
        SecurityInfo securityInfo = SecurityInfo.getInstance();
        securityInfo.setSecurityToken(userSession.getSecurityToken());
        securityInfo.setUserId(userSession.getUserId());
        securityInfo.setSaltValue(userSession.getSaltValue());
        securityInfo.setSessionId(userSession.getSessionId());
        socketRequestPayload.setSecurityInfo(securityInfo);

        String payloadAsString = socketRequestPayload.toString();
        log.info(payloadAsString);

        // Socket Communication with app
        LimoSocketClient locLimoSocketClient = new LimoSocketClient();
        String toReceive = locLimoSocketClient.processRequest(payloadAsString);

        SocketPayload socketPayloadResponse = SocketPayload.getInstance().toObject(toReceive);
        System.out.println(socketPayloadResponse);
    }

}
