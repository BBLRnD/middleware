package com.agent.middleware.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonUtil {

    public static Map<String, Integer> headerMap(String[] list) {
        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < list.length; i++) {
            map.put(list[i], i);
        }
        return map;
    }

    public static int keyPairOfMap(Map<String, Integer> map, String i){
        return map.get(i);
    }

    public static List<Map> getExceptionMap(String[] headerInfo, List<String[]> records) {
        List<Map> exceptionList = new ArrayList();
        for (int i = 0; i < records.size(); i++) {
            Map mMap = new HashMap();
            String[] str = records.get(i);
            for (int j = 0; j < headerInfo.length; j++) {
                mMap.put(headerInfo[j], str[j]);
            }
            exceptionList.add(mMap);
        }
        return exceptionList;
    }

}
