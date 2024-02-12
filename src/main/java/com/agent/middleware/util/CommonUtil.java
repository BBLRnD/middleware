package com.agent.middleware.util;

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
}
