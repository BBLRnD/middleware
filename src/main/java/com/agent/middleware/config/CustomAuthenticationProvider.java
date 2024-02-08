package com.agent.middleware.config;

import com.agent.middleware.entity.CustomUserDetails;
import com.agent.middleware.entity.UserInfo;
import com.agent.middleware.repository.UserRepository;
import com.bbl.servicepool.LimoSocketClient;
import com.bbl.util.model.CallingInfo;
import com.bbl.util.model.DeviceInfo;
import com.bbl.util.model.GenDataBlock;
import com.bbl.util.model.SocketPayload;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;

@Log4j2
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @SneakyThrows
    CustomUserDetails isValidUser(String username, String password) {
//        SocketPayload socketRequestPayload = SocketPayload.getInstance();
//        //1. calling info
//        CallingInfo callingInfo = CallingInfo.getInstance();
//        callingInfo.setVersionInfo("1.0.0");
//        callingInfo.setFuncCode("M");
//        callingInfo.setServiceName("DoLogin");
//        socketRequestPayload.setCallingInfo(callingInfo);
//
//        //2. Device Info
//        DeviceInfo deviceInfo = DeviceInfo.getInstance();
//        deviceInfo.setIpAddress(InetAddress.getLocalHost().getHostAddress());
//        socketRequestPayload.setDeviceInfo(deviceInfo);
//
//        //3. gen block
//        GenDataBlock genDataBlock = GenDataBlock.getInstance();
//        HashMap<String, String> formData = new HashMap<>();
//        formData.put("loginId", username);
//        formData.put("loginKey", password);
//        genDataBlock.setFormData(formData);
//        socketRequestPayload.setGenDataBlock(genDataBlock);
//
//        String payloadAsString = socketRequestPayload.toString();
//        log.info(payloadAsString);
//
//        // Socket Communication with app
//        LimoSocketClient locLimoSocketClient = new LimoSocketClient();
//        String toReceive = locLimoSocketClient.processRequest(payloadAsString);
//        log.info(toReceive);
//
//        SocketPayload socketPayloadResponse = SocketPayload.getInstance().toObject(toReceive);
//
//        System.out.println(socketPayloadResponse.getStatusBlock().getResponseCode());
//        if (socketPayloadResponse.getStatusBlock().
//                getResponseCode().equalsIgnoreCase("SUCCESS")) {
//            UserInfo userInfo = new UserInfo();
//            userInfo.setModules(socketPayloadResponse.getGenDataBlock().getValueByKey("appId"));
//            CustomUserDetails customUserDetails = new CustomUserDetails(userInfo);
//            return customUserDetails;
//        } else {
//            log.info(socketPayloadResponse.getStatusBlock().getResponseMessage());
//        }

        UserInfo userInfo = new UserInfo();
        userInfo.setId(202);
        userInfo.setModules("[\"OPERATIONS\", \"ACCESS_CONTROL\"]");
        userInfo.setFullName("Super Admin");
        userInfo.setRoles(Arrays.asList("USER","S_ADMIN"));
        userInfo.setUsername(username);
        CustomUserDetails customUserDetails = new CustomUserDetails(userInfo);
        return customUserDetails;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        CustomUserDetails customUserDetails = isValidUser(username, password);
        if (customUserDetails != null) {
            return new UsernamePasswordAuthenticationToken(
                    customUserDetails,
                    null,
                    customUserDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("Incorrect user credentials !!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
