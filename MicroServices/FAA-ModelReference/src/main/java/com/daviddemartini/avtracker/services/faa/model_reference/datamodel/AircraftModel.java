package com.daviddemartini.avtracker.services.faa.model_reference.datamodel;

import com.daviddemartini.avtracker.faa_datamodel.aircraft_type.AircraftType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.math3.optim.InitialGuess;
import com.daviddemartini.avtracker.faa_datamodel.engine_type.EngineType;
import com.daviddemartini.avtracker.faa_datamodel.category_code.CategoryCode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * New Contact object is based upon the MSG and other feed data
 * parsed from one of the following exemplar message strings
 *
 */

public class AircraftModel {

    private String faaModelId;
    private String manufacture;
    private String model;
    private String type;
    private String engine;
    private String category;

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
        this.load(message);
    }

    /**
     * Merge dump1090 pre-parsed list of fields
     *
     * @param message - CSV string of model data
     */
    public void load(String message) {

        // split on CSV marker
        List<String> parsedFields = Arrays.asList(message.split(","));

        // iterate the parsed fields
        for (int fieldNum = 0; fieldNum < parsedFields.size(); fieldNum++) {
            String fieldVal = parsedFields.get(fieldNum).trim();
            if (fieldVal != null && !fieldVal.trim().isEmpty()) {
                switch (fieldNum) {
                    case 0:
                        faaModelId = fieldVal;
                        break;
                    case 1:
                        manufacture = fieldVal;
                        break;
                    case 2:
                        model = fieldVal;
                        break;
                    case 3:
                        type = AircraftType.get(fieldVal);
                        break;
                    case 4:
                        engine = EngineType.get(fieldVal);
                        break;
                    case 5:
                        category = CategoryCode.get(fieldVal);
                        break;
                    default:
                        // not used at this time
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
     *  *** GETTERS ***
     */

    public String getFaaModelId() {
        return faaModelId;
    }

    public String getManufacture() {
        return manufacture;
    }

    public String getModel() {
        return model;
    }

    public String getType() {
        return type;
    }

    public String getEngine() {
        return engine;
    }

    public String getCategory() {
        return category;
    }

}

