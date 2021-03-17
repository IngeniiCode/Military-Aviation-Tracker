package com.daviddemartini.avtracker.services.faa.model_reference.datamodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * New Contact object is based upon the MSG and other feed data
 * parsed from one of the following exemplar message strings
 *
 */

public class AircraftModel {

    private long faaModelId;


    /**
     * Constructor
     *
     * Accepts dump1090 message string (.csv string) or undeclared
     * creates Aircraft data model object
     *
     * @param message - dump1090 port 30003 message
     */
    public AircraftModel(String message) {
        // execute merge operation
        this.merge(message);
    }

    public AircraftModel() { }

    /**
     * Merge dump1090 data message into object.
     *
     * @param message
     */
    public void merge(String message) {
        // parse the message into the fields
        merge(Arrays.asList(message.split(",")));
    }

    public void merge(AircraftModel aircraftModel) {
        // verify objects are the same, otherwise exit
        assert this.getClass().getName().equals(aircraftModel.getClass().getName());
        // iterate the fields and replace when not null
        for (Field field : this.getClass().getDeclaredFields()) {
            for (Field newField : aircraftModel.getClass().getDeclaredFields()) {
                if (field.getName().equals(newField.getName())) {
                    try {
                        field.set(this, newField.get(aircraftModel) == null ? field.get(this) : newField.get(aircraftModel));
                    } catch (IllegalAccessException ignore) {
                        //ignore.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Merge dump1090 pre-parsed list of fields
     *
     * @param parsedFields - pre-parsed list of Strings
     */
    public void merge(List<String> parsedFields) {

        // iterate the parsed fields
        for (int fieldNum = 0; fieldNum < parsedFields.size(); fieldNum++) {
            String fieldVal = parsedFields.get(fieldNum).trim();
            if (fieldVal != null && !fieldVal.trim().isEmpty()) {
                switch (fieldNum) {

                }
            }
        }

    }

    /**
     * Express self as a JSON string.
     *
     * @return - String of Json content
     */
    public String toJSON() throws IOException {
        // convert this data object to JSON
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    /**
     * Stripped down version of Json object representation
     * that excludes null fields
     *
     * @return - String of Json content
     * @throws IOException
     */
    public String toJSONLite() throws IOException {
        // convert this data object to JSON
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(this);
    }


    /**
     * ** GETTERS ***
     */


}

