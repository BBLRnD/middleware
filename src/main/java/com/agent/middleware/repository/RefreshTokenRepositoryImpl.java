package com.agent.middleware.repository;

import com.agent.middleware.entity.RefreshToken;
import com.agent.middleware.entity.UserInfo;
import com.bbl.servicepool.LimoSocketClient;
import com.bbl.util.model.*;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.Optional;

@Component
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository{
    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return Optional.empty();
    }

    @Override
    public void delete(RefreshToken refreshToken) {
    }

    @SneakyThrows
    @Override
    public void delete(UserInfo userInfo) {
        SocketPayload socketRequestPayload = SocketPayload.getInstance();
        //1. calling info
        CallingInfo callingInfo = CallingInfo.getInstance();
        callingInfo.setVersionInfo("1.0.0");
        callingInfo.setFuncCode("M");
        callingInfo.setServiceName("DoLogout");
        socketRequestPayload.setCallingInfo(callingInfo);

        //2. Device Info
        DeviceInfo deviceInfo = DeviceInfo.getInstance();
        deviceInfo.setIpAddress(InetAddress.getLocalHost().getHostAddress());
        socketRequestPayload.setDeviceInfo(deviceInfo);

        //3. Secusity
        SecurityInfo securityInfo = SecurityInfo.getInstance();
        // need to make dynamic
        securityInfo.setUserId(userInfo.getUsername());
        socketRequestPayload.setSecurityInfo(securityInfo);

        String payloadAsString = socketRequestPayload.toString();

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
