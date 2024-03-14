package com.agent.middleware.config;

import com.agent.middleware.constants.ServiceNameConstant;
import com.agent.middleware.dto.UserLoginDto;
import com.agent.middleware.dto.UserSession;
import com.agent.middleware.exception.AuthenticationException;
import com.agent.middleware.exception.SocketResponseException;
import com.bbl.servicepool.LimoSocketClient;
import com.bbl.util.deviceInfo.HashGen;
import com.bbl.util.model.CallingInfo;
import com.bbl.util.model.DeviceInfo;
import com.bbl.util.model.GenDataBlock;
import com.bbl.util.model.SocketPayload;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Log4j2
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserSession userSession;

    public CustomAuthenticationProvider(UserSession userSession) {
        this.userSession = userSession;
    }

    @SneakyThrows
    Boolean isValidUser(UserLoginDto loginDto) {
        SocketPayload socketRequestPayload = SocketPayload.getInstance();
        //1. calling info
        CallingInfo callingInfo = CallingInfo.getInstance();
        callingInfo.setVersionInfo("1.0.0");
        callingInfo.setFuncCode("M");
        callingInfo.setServiceName(ServiceNameConstant.LOGIN);
        socketRequestPayload.setCallingInfo(callingInfo);

        //2. Device Info

        HashGen hashGen = HashGen.getInstance();
        DeviceInfo deviceInfo = DeviceInfo.getInstance();
        deviceInfo.setIpAddress(InetAddress.getLocalHost().getHostAddress());
        deviceInfo.setProcessorId("1234");
        deviceInfo.setMacAddress("abcd");
        deviceInfo.setBrowser("chrome");

        deviceInfo.setDeviceHash(hashGen.getDeviceToken(deviceInfo.getIpAddress(), deviceInfo.getProcessorId(),
                deviceInfo.getMacAddress(), deviceInfo.getBrowser()));
        socketRequestPayload.setDeviceInfo(deviceInfo);

        //3. gen block
        GenDataBlock genDataBlock = GenDataBlock.getInstance();
        HashMap<String, String> formData = new HashMap<>();
        formData.put("loginId", loginDto.getUsername());
        formData.put("loginKey", loginDto.getPassword());
        if (loginDto.getIsForced() != null && loginDto.getIsForced()) {
            formData.put("forceLoginFlg", "Y");
        }
        genDataBlock.setFormData(formData);
        socketRequestPayload.setGenDataBlock(genDataBlock);

        String payloadAsString = socketRequestPayload.toString();
        log.info(payloadAsString);

        // Socket Communication with app
        LimoSocketClient locLimoSocketClient = new LimoSocketClient();
        String toReceive = locLimoSocketClient.processRequest(payloadAsString);
        log.info(toReceive);

        SocketPayload socketPayloadResponse = SocketPayload.getInstance().toObject(toReceive);

        log.info(socketPayloadResponse);

        if (socketPayloadResponse.getStatusBlock().
                getResponseCode().equalsIgnoreCase("SUCCESS")) {
            userSession.setModules("[\"OPERATIONS\", \"ACCESS_CONTROL\"]");
            userSession.setUserApplId(socketPayloadResponse.getGenDataBlock().getValueByKey("applId"));
            userSession.setPrefLangCode(socketPayloadResponse.getGenDataBlock().getValueByKey("prefLangCode"));
            userSession.setFullName(loginDto.getUsername());
            userSession.setRoles(Arrays.asList("USER"));
            userSession.setUsername(loginDto.getUsername());

            // Set Security Info
            userSession.setUserId(socketPayloadResponse.getSecurityInfo().getUserId());
            userSession.setSessionId(socketPayloadResponse.getSecurityInfo().getSessionId());
            userSession.setSecurityToken(socketPayloadResponse.getSecurityInfo().getSecurityToken());
            userSession.setSaltValue(socketPayloadResponse.getSecurityInfo().getSaltValue());
            userSession.setLoginTimeSuc(socketPayloadResponse.getGenDataBlock().getValueByKey("loginTimeSuc"));
            userSession.setLoginTimeFai(socketPayloadResponse.getGenDataBlock().getValueByKey("loginTimeFai"));
//            CustomUserDetails customUserDetails = new CustomUserDetails(userInfo);
            return true;
        } else if (socketPayloadResponse.getStatusBlock().
                getResponseCode().equalsIgnoreCase("FAILURE")) {
            throw new SocketResponseException(socketPayloadResponse.getStatusBlock().getResponseMessage(),
                    socketPayloadResponse.getStatusBlock().getErrorCode());
        }
        return false;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        UserLoginDto userLoginDto = (UserLoginDto) authentication.getCredentials();
        if (isValidUser(userLoginDto)) {
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            for (String role : userSession.getRoles()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
            return new UsernamePasswordAuthenticationToken(
                    userSession,
                    null,
                    authorities);
        } else {
            throw new AuthenticationException("Invalid User/Password !!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
