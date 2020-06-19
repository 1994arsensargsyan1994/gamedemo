package com.example.gamedemo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;


public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);


    public static String getStringValue(String jsonData, String key) throws IOException {
        JsonNode jsonNode = mapper.readTree(jsonData).get(key);
        return jsonNode == null ? null : jsonNode.asText();
    }

    public static <T> T deserialize(String payload, TypeReference<T> type) throws IOException {
        return mapper.readValue(payload, type);

    }

    public static <T> T deserialize(InputStream payload, TypeReference<T> type) throws IOException {
        return mapper.readValue(payload, type);
    }

    public static String serialise(Object obj) {

        String value = "";
        try {
            value = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("Error", e);
            e.printStackTrace();
        }
        return value;
    }

}
