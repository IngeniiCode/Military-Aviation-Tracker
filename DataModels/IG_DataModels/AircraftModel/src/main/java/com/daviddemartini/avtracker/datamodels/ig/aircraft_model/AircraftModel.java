package com.daviddemartini.avtracker.datamodels.ig.aircraft_model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.math3.optim.InitialGuess;
import com.daviddemartini.avtracker.datamodels.faa.aircraft_type.AircraftType;
import com.daviddemartini.avtracker.datamodels.faa.engine_type.EngineType;
import com.daviddemartini.avtracker.datamodels.faa.category_code.CategoryCode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Aircraft model to map faa modelID to usable text such as the manufature and model of
 * aircraft, as well a some other interesting bits of information such as engine type, count
 * and category of craft (land, sea, etc.)
 *
 */

public class AircraftModel {

    private String faaModelId;
    private String manufacture;
    private String model;
    private String type;
    private String engine;
    private String category;
    // non-model elements
    ObjectMapper objectMapper;

    /**
     * Constructor
     *
     * Accepts dump1090 message string (.csv string) or undeclared
     * creates Aircraft data model object
     *
     * @param message - dump1090 port 30003 message
     */
    public AircraftModel(String message) {
        // instanciate object mapper
        objectMapper = new ObjectMapper();
        // execute merge operation
        this.load(message);
    }

    /**
     * Merge dump1090 pre-parsed list of fields
     *
     * @param message - CSV string of model data
     */
    public void load(String message) {

        try {
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
        catch (Exception e){
            System.err.printf("Message parsing exception %s occured in [%s]\n",e.getMessage(),message);
        }
    }

    /**
     * Express self as a JSON string.
     *
     * @return - String of Json content
     */
    public String toJSON() throws IOException {
        // convert this data object to JSON
        return objectMapper.writeValueAsString(this);
    }

    /**
     * Stripped down version of Json object representation
     * that excludes null fields
     *
     * @return - String of Json content
     * @throws IOException
     */
    public String toJSONLite() throws IOException {
        // convert this data object to JSON, without any null fields
        objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper.writeValueAsString(this);
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

