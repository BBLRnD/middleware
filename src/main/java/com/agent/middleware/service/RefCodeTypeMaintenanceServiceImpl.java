package com.agent.middleware.service;

import com.agent.middleware.constants.ServiceNameConstant;
import com.agent.middleware.dto.UserSession;
import com.agent.middleware.dto.coreconfig.RefTypeMaintenanceDto;
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

    @Override
    public StatusBlock save(RefTypeMaintenanceDto refTypeDto) {
        SocketPayload socketRequestPayload = SocketPayload.getInstance();
        //1. calling info
        CallingInfo callingInfo = CallingInfo.getInstance();
        callingInfo.setVersionInfo("1.0.0");
        callingInfo.setFuncCode(refTypeDto.getFunctionCode());
        callingInfo.setMenuId(refTypeDto.getMenuId());
        callingInfo.setServiceName(ServiceNameConstant.SAVE_REFFCODE_TYPE);
        socketRequestPayload.setCallingInfo(callingInfo);

        // device info
        socketRequestPayload.setDeviceInfo(getSocketDeviceInfoPayload());
        // security info
        socketRequestPayload.setSecurityInfo(getSocketSecurityInfoPayload());

        GenDataBlock genDataBlock = GenDataBlock.getInstance();
        HashMap<String, String> formData = new HashMap<>();

        formData.put("refCodeTypeDesc",refTypeDto.getRefCodeTypeDesc());
        formData.put("lchgTime",refTypeDto.getLchgTime());
        formData.put("depFlg",refTypeDto.getDependentFlg());
        formData.put("depRefCodeType",refTypeDto.getDependentRefCodeType());
        formData.put("depRefCodeTypeDesc", refTypeDto.getDepRefCodeTypeDesc());
        formData.put("refCodeLen", refTypeDto.getRefCodeLength());

        genDataBlock.setFormData(formData);
        socketRequestPayload.setGenDataBlock(genDataBlock);

        CriteriaBlock criteriaBlock = CriteriaBlock.getInstance();
        HashMap<String, String> criteriaData = new HashMap<>();

        criteriaData.put("refCodeType",refTypeDto.getRefCodeType());
        if(refTypeDto.getFunctionCode().equalsIgnoreCase("C"))
            criteriaData.put("refCodeTypeNew",refTypeDto.getNewRefCodeType());

        criteriaBlock.setCriteriaData(criteriaData);
        socketRequestPayload.setCriteriaBlock(criteriaBlock);
        String payloadAsString = socketRequestPayload.toString();

        // Socket Communication with app
        LimoSocketClient locLimoSocketClient = new LimoSocketClient();
        String toReceive = locLimoSocketClient.processRequest(payloadAsString);
        SocketPayload socketPayloadResponse = SocketPayload.getInstance().toObject(toReceive);
        System.out.println(socketPayloadResponse);
        if (socketPayloadResponse.getStatusBlock().
                getResponseCode().equalsIgnoreCase("SUCCESS")) {
            return socketPayloadResponse.getStatusBlock();
        }else{
            throw new SocketResponseException(socketPayloadResponse.getStatusBlock().getResponseMessage(),
                    socketPayloadResponse.getStatusBlock().getErrorCode());
        }

    }

    @Override
    public GenDataBlock getRefCodeTypeDetail(String functionCode,String refCodeType) {
        SocketPayload socketRequestPayload = SocketPayload.getInstance();
        //1. calling info
        CallingInfo callingInfo = CallingInfo.getInstance();
        callingInfo.setVersionInfo("1.0.0");
        callingInfo.setFuncCode(functionCode);
        callingInfo.setServiceName(ServiceNameConstant.FETCH_REFCODE_TYPE);
        socketRequestPayload.setCallingInfo(callingInfo);

        // device info
        socketRequestPayload.setDeviceInfo(getSocketDeviceInfoPayload());
        // security info
        socketRequestPayload.setSecurityInfo(getSocketSecurityInfoPayload());

        //3. gen block
        CriteriaBlock criteriaBlock = CriteriaBlock.getInstance();
        HashMap<String, String> criteriaData = new HashMap<>();
        criteriaData.put("refCodeType",refCodeType);
        criteriaBlock.setCriteriaData(criteriaData);
        socketRequestPayload.setCriteriaBlock(criteriaBlock);
        String payloadAsString = socketRequestPayload.toString();

        System.out.println(payloadAsString);

        // Socket Communication with app
        LimoSocketClient locLimoSocketClient = new LimoSocketClient();
        String toReceive = locLimoSocketClient.processRequest(payloadAsString);
        System.out.println(toReceive);
        SocketPayload socketPayloadResponse = SocketPayload.getInstance().toObject(toReceive);

        System.out.println(socketPayloadResponse.getStatusBlock().getResponseCode());
        if (socketPayloadResponse.getStatusBlock().
                getResponseCode().equalsIgnoreCase("SUCCESS")) {
            return socketPayloadResponse.getGenDataBlock();
        }else{
            throw new SocketResponseException(socketPayloadResponse.getStatusBlock().getResponseMessage(),
                    socketPayloadResponse.getStatusBlock().getErrorCode());
        }

    }
    @Override
    public ListBlock getRefTypeList(RefTypeMaintenanceDto refTypeMaintenanceDto) {
        SocketPayload socketRequestPayload = SocketPayload.getInstance();
        //1. calling info
        CallingInfo callingInfo = CallingInfo.getInstance();
        callingInfo.setVersionInfo("1.0.0");
        callingInfo.setFuncCode(refTypeMaintenanceDto.getFunctionCode());
        callingInfo.setServiceName(ServiceNameConstant.FETCH_REFCODE_TYPE_LIST);
        socketRequestPayload.setCallingInfo(callingInfo);

        // device info
        socketRequestPayload.setDeviceInfo(getSocketDeviceInfoPayload());
        // security info
        socketRequestPayload.setSecurityInfo(getSocketSecurityInfoPayload());

        //3. gen block
        CriteriaBlock criteriaBlock = CriteriaBlock.getInstance();
        HashMap<String, String> criteriaData = new HashMap<>();
        criteriaData.put("refCodeTypeDesc",refTypeMaintenanceDto.getRefCodeType());
        criteriaData.put("numOfRecsPerPage",refTypeMaintenanceDto.getNumOfRecsPerPage());
        criteriaData.put("pageNum",refTypeMaintenanceDto.getPageNum());
        criteriaBlock.setCriteriaData(criteriaData);
        socketRequestPayload.setCriteriaBlock(criteriaBlock);
        String payloadAsString = socketRequestPayload.toString();

        System.out.println(payloadAsString);

        // Socket Communication with app
        LimoSocketClient locLimoSocketClient = new LimoSocketClient();
        //String toReceive = locLimoSocketClient.processRequest(payloadAsString);
        String toReceive;
        if(refTypeMaintenanceDto.getPageNum().equalsIgnoreCase("1")){
            toReceive = "[statusBlock=[responseCode=SUCCESS|errorCode=0|responseMessage=Reference Type Data]]~~" +
                    "[listBlock=[numberOfRecs=3][headerInfo=refCodeType|refCodeTypeDesc]" +
                    "[message=Total 3 Records Found][curPageNum=1][maxPageNum=1]" +
                    "[dataBlocks=01|APPLICATION ID~02|LANGUAGE CODE~03|APPLICATION MODULE~03|APPLICATION MODULE~03|APPLICATION MODULE~03|APPLICATION MODULE~03|APPLICATION MODULE~03|APPLICATION MODULE~03|APPLICATION MODULE~03|APPLICATION MODULE~03|APPLICATION MODULE]]";
        }else{
            toReceive = "[callingInfo=[serviceName=FetchRefCodeTypeList|versionInfo=1.0.0|funcCode=M]]~~" +
                    "[statusBlock=[responseCode=SUCCESS|errorCode=0|responseMessage=Reference Type Data]]~~" +
                    "[listBlock=[numberOfRecs=1][headerInfo=refCodeType|refCodeTypeDesc]" +
                    "[message=Total 3 Records Found][curPageNum=2][maxPageNum=1]" +
                    "[dataBlocks=20|APPLICATION ID~02|APPLICATION ID]]";
        }

        SocketPayload socketPayloadResponse = SocketPayload.getInstance().toObject(toReceive);

        System.out.println(socketPayloadResponse);

        if (socketPayloadResponse.getStatusBlock().
                getResponseCode().equalsIgnoreCase("SUCCESS")) {

            return socketPayloadResponse.getListBlock();
        }else{
            throw new SocketResponseException(socketPayloadResponse.getStatusBlock().getResponseMessage(),
                    socketPayloadResponse.getStatusBlock().getErrorCode());
        }
    }

    @SneakyThrows
    private DeviceInfo getSocketDeviceInfoPayload() {
        HashGen hashGen = HashGen.getInstance();
        DeviceInfo deviceInfo = DeviceInfo.getInstance();
        deviceInfo.setIpAddress(InetAddress.getLocalHost().getHostAddress());
        deviceInfo.setProcessorId("1234");
        deviceInfo.setMacAddress("abcd");
        deviceInfo.setBrowser("chrome");

        deviceInfo.setDeviceHash(hashGen.getDeviceToken(deviceInfo.getIpAddress(),deviceInfo.getProcessorId(),
                deviceInfo.getMacAddress(),deviceInfo.getBrowser()));

        return deviceInfo;
    }

    @SneakyThrows
    private SecurityInfo getSocketSecurityInfoPayload() {
        SecurityInfo securityInfo = SecurityInfo.getInstance();
        securityInfo.setSecurityToken(userSession.getSecurityToken());
        securityInfo.setUserId(userSession.getUserId());
        securityInfo.setSaltValue(userSession.getSaltValue());
        securityInfo.setSessionId(userSession.getSessionId());
        return securityInfo;
    }
}
