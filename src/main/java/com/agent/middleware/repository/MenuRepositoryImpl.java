package com.agent.middleware.repository;

import com.agent.middleware.constants.ServiceNameConstant;
import com.agent.middleware.dto.UserSession;
import com.agent.middleware.dto.menu.MenuDto;
import com.agent.middleware.dto.menu.MenuResponseDto;
import com.agent.middleware.entity.Menu;
import com.agent.middleware.enums.MenuType;
import com.agent.middleware.enums.Module;
import com.agent.middleware.util.CommonUtil;
import com.bbl.servicepool.LimoSocketClient;
import com.bbl.util.deviceInfo.HashGen;
import com.bbl.util.model.*;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MenuRepositoryImpl implements MenuRepository{

    private final UserSession userSession;

    public MenuRepositoryImpl(UserSession userSession) {
        this.userSession = userSession;
    }

    @Override
    public Menu save(Menu menu) {
        return null;
    }

    @SneakyThrows
    @Override
    public MenuResponseDto findAllByModule(Module module) {
        SocketPayload socketRequestPayload = SocketPayload.getInstance();
        //1. calling info
        CallingInfo callingInfo = CallingInfo.getInstance();
        callingInfo.setVersionInfo("1.0.0");
        callingInfo.setFuncCode("M");
        callingInfo.setServiceName(ServiceNameConstant.MENU_TREE);
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
        // need to make dynamic
//        SecurityToken token1 = SecurityToken.getInstance();
//        securityInfo.setUserId(token1.getUserId());
//        securityInfo.setSessionId(token1.getSessionId());
//        securityInfo.setSecurityToken(token1.getSecurityToken());
//        securityInfo.setSaltValue(token1.getSaltValue());
//        socketRequestPayload.setSecurityInfo(securityInfo);

        //3. gen block
        GenDataBlock genDataBlock = GenDataBlock.getInstance();
        HashMap<String, String> formData = new HashMap<>();
        // need to make dynamic
        formData.put("applId",userSession.getUserApplId());
        formData.put("prefLangCode",userSession.getPrefLangCode());
        genDataBlock.setFormData(formData);
        socketRequestPayload.setGenDataBlock(genDataBlock);

        String payloadAsString = socketRequestPayload.toString();

        System.out.println(payloadAsString);

        // Socket Communication with app
        LimoSocketClient locLimoSocketClient = new LimoSocketClient();
        String toReceive = locLimoSocketClient.processRequest(payloadAsString);
        SocketPayload socketPayloadResponse = SocketPayload.getInstance().toObject(toReceive);

        System.out.println(socketPayloadResponse);

        System.out.println(socketPayloadResponse.getStatusBlock().getResponseCode());
        MenuResponseDto menuResponseDto = new MenuResponseDto();
        if (socketPayloadResponse.getStatusBlock().
                getResponseCode().equalsIgnoreCase("SUCCESS")) {
            menuResponseDto = getMenus(socketPayloadResponse.getListBlock(), module);
        }
        return menuResponseDto;
    }


    private MenuResponseDto getMenus(ListBlock listBlock, Module module){
        List<MenuDto> layerZero = new ArrayList<>();
        List<MenuDto> layerOne = new ArrayList<>();
        List<MenuDto> layerTwo = new ArrayList<>();
        List<String[]> dataBlock = (listBlock == null ? new ArrayList<>(): listBlock.getDataBlock());
        if(dataBlock.size()<= 0){
            return new MenuResponseDto();
        }
        Map<String, Integer> header = CommonUtil.headerMap(listBlock.getHeaderInfo());

        for(String[] strings: dataBlock)
        {
            MenuDto menuDto = new MenuDto();
            menuDto.setId(strings[header.get("menuId")]);
            menuDto.setComponent(strings[header.get("param1")]);
            menuDto.setIcon("");
            menuDto.setTitle(StringEscapeUtils.unescapeJava(strings[header.get("menuDesc")]));
            menuDto.setModule(module);
            menuDto.setParentId(strings[header.get("parentMenuId")]);
            menuDto.setRole("ROLE_USER");
            switch (Integer.parseInt(strings[header.get("level")])) {
                case 1 -> {
                    menuDto.setHasChildren(true);
                    menuDto.setLayer(0);
                    menuDto.setType(MenuType.parent);
                    layerZero.add(menuDto);
                }
                case 2 -> {
                    menuDto.setHasChildren(true);
                    menuDto.setLayer(1);
                    menuDto.setType(MenuType.group);
                    layerOne.add(menuDto);
                }
                case 3 -> {
                    menuDto.setHasChildren(false);
                    menuDto.setType(MenuType.component);
                    menuDto.setLayer(2);
                    layerTwo.add(menuDto);
                }
                default -> {
                }
            }


        }
        MenuResponseDto menuResponseDto = new MenuResponseDto();
        menuResponseDto.setLayerZero(layerZero);
        menuResponseDto.setLayerOne(layerOne);
        menuResponseDto.setLayerTwo(layerTwo);
        return menuResponseDto;
    }
}
