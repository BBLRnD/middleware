package com.agent.middleware.repository;

import com.agent.middleware.dto.UserSession;
import com.agent.middleware.entity.RefreshToken;
import com.bbl.servicepool.LimoSocketClient;
import com.bbl.util.deviceInfo.HashGen;
import com.bbl.util.model.*;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Component
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {
    private final UserSession userSession;

    public RefreshTokenRepositoryImpl(UserSession userSession) {
        this.userSession = userSession;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return Optional.empty();
    }

    @Override
    public void delete(RefreshToken refreshToken) {
    }

    @SneakyThrows
    @Override
    public void delete() {
        SocketPayload socketRequestPayload = SocketPayload.getInstance();
        //1. calling info
        CallingInfo callingInfo = CallingInfo.getInstance();
        callingInfo.setVersionInfo("1.0.0");
        callingInfo.setFuncCode("M");
        callingInfo.setServiceName("DoLogout");
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

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return refreshToken;
    }
}
