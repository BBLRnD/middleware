package com.agent.middleware.config;

import com.agent.middleware.dto.UserLoginDto;
import com.agent.middleware.entity.CustomUserDetails;
import com.agent.middleware.entity.UserInfo;
import com.agent.middleware.exception.ABException;
import com.agent.middleware.util.CommonUtil;
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
import org.springframework.security.core.AuthenticationException;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @SneakyThrows
    CustomUserDetails isValidUser(UserLoginDto loginDto) {
        SocketPayload socketRequestPayload = SocketPayload.getInstance();
        //1. calling info
        CallingInfo callingInfo = CallingInfo.getInstance();
        callingInfo.setVersionInfo("1.0.0");
        callingInfo.setFuncCode("M");
        callingInfo.setServiceName("DoLogin");
        socketRequestPayload.setCallingInfo(callingInfo);

        //2. Device Info

        HashGen hashGen = HashGen.getInstance();
        DeviceInfo deviceInfo = DeviceInfo.getInstance();
        deviceInfo.setIpAddress(InetAddress.getLocalHost().getHostAddress());
        deviceInfo.setProcessorId("1234");
        deviceInfo.setMacAddress("abcd");
        deviceInfo.setBrowser("chrome");

        deviceInfo.setDeviceHash(hashGen.getDeviceToken(deviceInfo.getIpAddress(),deviceInfo.getProcessorId(),
                                                        deviceInfo.getMacAddress(),deviceInfo.getBrowser()));
        socketRequestPayload.setDeviceInfo(deviceInfo);

        //3. gen block
        GenDataBlock genDataBlock = GenDataBlock.getInstance();
        HashMap<String, String> formData = new HashMap<>();
        formData.put("loginId", loginDto.getUsername());
        formData.put("loginKey", loginDto.getPassword());
        if(loginDto.getIsForced()!=null && loginDto.getIsForced()){
            formData.put("forceLoginFlg","Y");
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
            UserInfo userInfo = new UserInfo();
            userInfo.setId(202);
            userInfo.setModules("[\"OPERATIONS\", \"ACCESS_CONTROL\"]");
            userInfo.setUserApplId(socketPayloadResponse.getGenDataBlock().getValueByKey("applId"));
            userInfo.setPrefLangCode(socketPayloadResponse.getGenDataBlock().getValueByKey("prefLangCode"));
            userInfo.setFullName(loginDto.getUsername());
            userInfo.setRoles(Arrays.asList("USER", "S_ADMIN"));
            userInfo.setUsername(loginDto.getUsername());

            // Set Security Info
            userInfo.setUserId(socketPayloadResponse.getSecurityInfo().getUserId());
            userInfo.setSessionId(socketPayloadResponse.getSecurityInfo().getSessionId());
            userInfo.setSecurityToken(socketPayloadResponse.getSecurityInfo().getSecurityToken());
            userInfo.setSaltValue(socketPayloadResponse.getSecurityInfo().getSaltValue());

            userInfo.setDeviceInoSuc(socketPayloadResponse.getGenDataBlock().getValueByKey("deviceInoSuc"));
            userInfo.setLoginTimeSuc(socketPayloadResponse.getGenDataBlock().getValueByKey("loginTimeSuc"));
            userInfo.setLoginIpSuc(socketPayloadResponse.getGenDataBlock().getValueByKey("loginIpSuc"));
            userInfo.setLocationInfoSuc(socketPayloadResponse.getGenDataBlock().getValueByKey("locationInfoSuc"));
            userInfo.setNewUserFlg(socketPayloadResponse.getGenDataBlock().getValueByKey("newUserFlg"));

            CustomUserDetails customUserDetails = new CustomUserDetails(userInfo);
            return customUserDetails;
        } else if (socketPayloadResponse.getStatusBlock().
                getResponseCode().equalsIgnoreCase("FAILURE")) {
            if (socketPayloadResponse.getExceptionBlock() != null) {
                List<Map> exceptionList = CommonUtil.getExceptionMap(socketPayloadResponse.getExceptionBlock().getHeaderInfo(),
                        socketPayloadResponse.getExceptionBlock().getRecords());
                throw new ABException.GeneralException(Integer.parseInt(exceptionList.get(0).get("errorCode").toString()),
                        exceptionList.get(0).get("errorMessage").toString());
            }
        }
        return null;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        UserLoginDto userLoginDto =(UserLoginDto) authentication.getCredentials();
        System.out.println(userLoginDto.getIsForced());
        CustomUserDetails customUserDetails = isValidUser(userLoginDto);
        if (customUserDetails != null) {
            return new UsernamePasswordAuthenticationToken(
                    customUserDetails,
                    null,
                    customUserDetails.getAuthorities());
        } else {
            throw new ABException.AuthenticationException("Invalid User/Password !!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
