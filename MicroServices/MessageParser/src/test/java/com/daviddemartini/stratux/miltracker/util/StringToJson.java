package com.daviddemartini.avtracker.services.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class StringToJson {

    /**
     * Simple class to accept a string and return as a Json object of sorts.
     * @param jsonString
     * @return
     * @throws IOException
     */
    public static JsonNode parse(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, JsonNode.class);
    }

}
