package com.agent.middleware.repository;

import com.agent.middleware.entity.Menu;
import com.agent.middleware.enums.Module;
import com.bbl.servicepool.LimoSocketClient;
import com.bbl.util.model.CallingInfo;
import com.bbl.util.model.GenDataBlock;
import com.bbl.util.model.SecurityInfo;
import com.bbl.util.model.SocketPayload;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class MenuRepositoryImpl implements MenuRepository{

    @Override
    public Menu save(Menu menu) {
        return null;
    }

    @Override
    public List<Menu> findAllByModuleAndLayer(Module module, Integer layer) {

        SocketPayload socketRequestPayload = SocketPayload.getInstance();
        //1. calling info
        CallingInfo callingInfo = CallingInfo.getInstance();
        callingInfo.setVersionInfo("1.0.0");
        callingInfo.setFuncCode("M");
        callingInfo.setServiceName("GetMenuTree");
        socketRequestPayload.setCallingInfo(callingInfo);


        SecurityInfo securityInfo = SecurityInfo.getInstance();
        // need to make dynamic
        securityInfo.setUserId("ADMIN1");
        socketRequestPayload.setSecurityInfo(securityInfo);

        //3. gen block
        GenDataBlock genDataBlock = GenDataBlock.getInstance();
        HashMap<String, String> formData = new HashMap<>();
        // need to make dynamic
        formData.put("applId","OPERATION");
        genDataBlock.setFormData(formData);
        socketRequestPayload.setGenDataBlock(genDataBlock);

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

            //System.out.println(socketPayloadResponse.getListBlock().getDataBlock());

        }
        return new ArrayList<>();
    }
}
