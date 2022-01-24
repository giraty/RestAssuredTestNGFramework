package org.selenium.pom.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.Product;

import java.io.IOException;
import java.io.InputStream;

public class JacksonUtils {

    public static <T> T deserializeJson(String fileName, Class<T> T) throws IOException {
        InputStream is = JacksonUtils.class.getClassLoader().getResourceAsStream(fileName);//dir automatic search
        ObjectMapper objectMapper = new ObjectMapper();
        //ConfigLoader configLoader = new ConfigLoader();
        return objectMapper.readValue(is, T);
    }

}
