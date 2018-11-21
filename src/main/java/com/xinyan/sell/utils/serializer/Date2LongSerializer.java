package com.xinyan.sell.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * @Author: ZengQingQuan
 * @Description:
 * @Date: Create in 22:36 2018/11/21 0021
 */
public class Date2LongSerializer extends JsonSerializer<Date> {

    /**
     * 吧 date 转换成 long
     * @param date
     * @param jsonGenerator
     * @param serializerProvider
     * @throws IOException
     * @throws JsonProcessingException
     */
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        //除以1000
        jsonGenerator.writeNumber(date.getTime() / 1000);
    }
}

