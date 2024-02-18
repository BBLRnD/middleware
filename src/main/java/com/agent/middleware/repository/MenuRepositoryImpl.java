package com.agent.middleware.repository;

import com.agent.middleware.dto.menu.MenuDto;
import com.agent.middleware.dto.menu.MenuResponseDto;
import com.agent.middleware.entity.Menu;
import com.agent.middleware.entity.UserInfo;
import com.agent.middleware.enums.MenuType;
import com.agent.middleware.enums.Module;
import com.agent.middleware.service.UserServiceImpl;
import com.agent.middleware.util.CommonUtil;
import com.bbl.servicepool.LimoSocketClient;
import com.bbl.util.model.*;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuRepositoryImpl implements MenuRepository{

    @Autowired
    UserServiceImpl userService;
    @Override
    public Menu save(Menu menu) {
        return null;
    }

    @Override
    public MenuResponseDto findAllByModule(Module module) {

        UserInfo loggedInUser = userService.getLoggedInUser();

        SocketPayload socketRequestPayload = SocketPayload.getInstance();
        //1. calling info
        CallingInfo callingInfo = CallingInfo.getInstance();
        callingInfo.setVersionInfo("1.0.0");
        callingInfo.setFuncCode("M");
        callingInfo.setServiceName("GetMenuTree");
        socketRequestPayload.setCallingInfo(callingInfo);


        SecurityInfo securityInfo = SecurityInfo.getInstance();
        // need to make dynamic
        securityInfo.setUserId(loggedInUser.getUsername());
        socketRequestPayload.setSecurityInfo(securityInfo);

        //3. gen block
        GenDataBlock genDataBlock = GenDataBlock.getInstance();
        HashMap<String, String> formData = new HashMap<>();
        // need to make dynamic
        formData.put("applId",loggedInUser.getUserApplId());
        genDataBlock.setFormData(formData);
        socketRequestPayload.setGenDataBlock(genDataBlock);

        String payloadAsString = socketRequestPayload.toString();

        System.out.println(payloadAsString);

        // Socket Communication with app
        LimoSocketClient locLimoSocketClient = new LimoSocketClient();
        String toReceive = locLimoSocketClient.processRequest(payloadAsString);
        SocketPayload socketPayloadResponse = SocketPayload.getInstance().toObject(toReceive);

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
        List<String[]> dataBlock = listBlock.getDataBlock();
        Map<String, Integer> header = CommonUtil.headerMap(listBlock.getHeaderInfo());

        for(String[] strings: dataBlock)
       {
           MenuDto menuDto = new MenuDto();
           menuDto.setId(strings[header.get("menuId")]);
           menuDto.setComponent(strings[header.get("param1")]);
           menuDto.setIcon("");
           menuDto.setTitle(strings[header.get("menuDesc")]);
           menuDto.setModule(module);
           menuDto.setParentId(strings[header.get("parentMenuId")]);
           menuDto.setRole("ROLE_USER");
           switch (Integer.parseInt(strings[header.get("level")])){
               case 1 : {
                   menuDto.setHasChildren(true);
                   menuDto.setLayer(0);
                   menuDto.setType(MenuType.parent);
                   layerZero.add(menuDto);
                   break;
               }
               case 2 : {
                   menuDto.setHasChildren(true);
                   menuDto.setLayer(1);
                   menuDto.setType(MenuType.group);
                   layerOne.add(menuDto);
                   break;
               }
               case 3: {
                   menuDto.setHasChildren(false);
                   menuDto.setType(MenuType.component);
                   menuDto.setLayer(2);
                   layerTwo.add(menuDto);
                   break;
               }
               default: break;
           }


       }
        MenuResponseDto menuResponseDto = new MenuResponseDto();
        menuResponseDto.setLayerZero(layerZero);
        menuResponseDto.setLayerOne(layerOne);
        menuResponseDto.setLayerTwo(layerTwo);
        return menuResponseDto;
    }
}
