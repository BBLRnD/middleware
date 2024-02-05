package com.agent.middleware.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class MapperUtil {

    private final ObjectMapper objectMapper;

    public MapperUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public <O> String objectToJson(O obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    public <T> T stringToObject(String json, Class<T> valueType) throws JsonProcessingException {
        return objectMapper.readValue(json, valueType);
    }


    public Map convertToDotNotation(Object obj) {
        StringBuilder result = new StringBuilder();
        HashMap<String, String> map = new HashMap<>();
        convert(obj, result, map);
        return map;
    }

    private static void convert(Object obj, StringBuilder result, HashMap<String, String> map) {
        if (obj == null) {
            return;
        }

        Class<?> clazz = obj.getClass();
        if (clazz.getName().equals("java.lang.String")) {
            return;
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object fieldValue = field.get(obj);

                if (fieldValue != null) {
                    if (fieldValue != null && !field.getType().isPrimitive() && !fieldValue.getClass().getName().startsWith("java.lang")
                            && !fieldValue.getClass().getName().equals("java.util.ArrayList")) {
                        convert(fieldValue, result.append(field.getName()).append("."), map);
                    } else if (fieldValue.getClass().getName().startsWith("java.lang")) {
                        StringBuilder data = new StringBuilder(result);
                        map.put(data.append(field.getName()).toString(), fieldValue + "");
                        convert(fieldValue, result, map);
                    } else if (fieldValue.getClass().getName().equals("java.util.ArrayList")) {

                        List<?> list = Arrays.asList((ArrayList<?>) fieldValue).get(0);
                        for (int i = 0; i < list.size(); i++) {
                            StringBuilder data = new StringBuilder(result);
                            if (list.get(i) instanceof java.lang.String) {
                                map.put(data.append(field.getName()).append("[").append(i).append("]").toString(), list.get(i) + "");
                            } else {
                                List<?> list2 = Arrays.asList((ArrayList<?>) fieldValue).get(0);
                                for (int j = 0; j < list2.size(); j++) {
                                    StringBuilder data1 = new StringBuilder(result);
                                    if (list.get(j) instanceof java.lang.String) {
                                        map.put(data1.append(field.getName()).append("[").append(j).append("]").toString(), list.get(j) + "");
                                    }
                                }
                            }

                        }

                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
//            result = new StringBuilder("");
        }
    }


//    Map<String,String> initialSplit(String payloadAsString){
//        String pattern = ;
//        Pattern regex = Pattern.compile(pattern);
//        Matcher matcher = regex.matcher(payloadAsString);
//        if (matcher.find()) {
//            String output = matcher.group();
//            System.out.println(output);
//            CallingInfo callingInfo1 = new CallingInfo().callingInfo(output);
//        } else {
//            System.out.println("No match found.");
//        }
//    }
}
