package com.chatapp.tokens.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;

public class JsonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true);
    }

    public String toJson(Object object) throws DeserializableException {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new DeserializableException(e);
        }
    }

    public <T> T getObject(JsonNode jsonNode, Class<T> valueType) throws DeserializableException {
        try {
            return objectMapper.treeToValue(jsonNode, valueType);
        } catch (IOException e) {
            throw new DeserializableException(e);
        }
    }

    public <T> T getObject(String inputString, Class<T> valueType) throws DeserializableException {
        try {
            return objectMapper.readValue(inputString, valueType);
        } catch (IOException e) {
            throw new DeserializableException(e);
        }
    }

    public void writeObject(OutputStream output, Object object) {
        try {
            objectMapper.writeValue(output, object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonNode getJsonNode(String inputString) throws DeserializableException {
        try {
            return objectMapper.readTree(inputString);
        } catch (JsonProcessingException e) {
            throw new DeserializableException(e);
        }
    }

}
