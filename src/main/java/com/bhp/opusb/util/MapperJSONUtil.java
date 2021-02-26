package com.bhp.opusb.util;

import com.bhp.opusb.service.MPurchaseOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class MapperJSONUtil {
    private final Logger log = LoggerFactory.getLogger(MPurchaseOrderService.class);
    public static String prettyLog (Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        }catch (IOException e){
            return null;
        }
    }
}
