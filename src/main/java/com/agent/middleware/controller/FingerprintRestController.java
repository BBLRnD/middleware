package com.agent.middleware.controller;

import com.agent.middleware.dto.RequestData;
import com.bbl.util.enums.HeaderContentType;
import com.bbl.util.utils.HttpHelperUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class FingerprintRestController {
    @PostMapping("/public/fingerprint")
    public RequestData getRequestData(@RequestBody RequestData requestData) throws IOException {

        // rest template call to ec service....
        String ecUrl ="http://127.0.0.1:8099/redirect";
//        Http http = new HttpGet(ecUrl);
//        HashMap<String, String> headerMap = new HashMap<String, String>();
//        headerMap.put("Content-Type", "application/json");
//
       HttpHelperUtil httpHelperUtil = new HttpHelperUtil();
         String url = httpHelperUtil.httpGetCall(ecUrl, null, HeaderContentType.APPLICATION_TEXT, String.class);
        System.out.println("Url ::: " + url);
         requestData.setUrl(url);
        requestData.setToken("1234");
        //headerMap.put("Authorization", "Bearer " + accessToken);
//        http.setHeaders(headerMap);
//        int response = http.execute();
//        String responseBody = "";
//        if (response == 200) {
//            responseBody = http.getResponseBody().toString();
//
//            System.out.println("result "+responseBody);
//
//        }
        // you will get an url(dynamic) that you need to forward to frontend

        return requestData;
    }

    private String generateRedirectUrl(String url, String token) {
        return url + "?token=" + token;
    }
}
