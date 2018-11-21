package com.xinyan.sell.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created
 * 2017-07-04 01:30
 * JSON 和 Java 对象转换工具
 */
public final class JsonUtil {

    public static ObjectMapper objectMapper;

    static {
        if (objectMapper == null){
            objectMapper = new ObjectMapper();
            //ALLOW_UNQUOTED_FIELD_NAMES 允许属性名称没有引号
            objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,true);
        }
    }

    /**
     * json字符串转换为相应的JavaBean对象
     * @param jsonStr
     * @param valueType
     * @param <T>
     * @return
     */
    public  static <T> T readValue(String jsonStr, Class<T> valueType){
        try {
            return objectMapper.readValue(jsonStr, valueType);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * json数组转List
     * @param jsonStr
     * @param valueTypeRef
     * @param <T>
     * @return
     */
    public  static <T> T readValue(String jsonStr, TypeReference<T> valueTypeRef){
        try {
            return objectMapper.readValue(jsonStr, valueTypeRef);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 吧JavaBean转换为json字符串
     * @param object
     * @return
     */
    public static String toJson(Object object){
        try {
//         return objectMapper.writeValueAsString(object);  //一行输出
            //格式化输出
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

}
