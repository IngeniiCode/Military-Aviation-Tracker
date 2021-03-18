package com.daviddemartini.avtracker.services.new_contacts_pipeline.datamodel;

import com.daviddemartini.avtracker.services.new_contacts_pipeline.util.MilCallsignStringParse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * New Contact object is based upon the MSG and other feed data
 * parsed from one of the following exemplar message strings
 *
 */

public class NewContact {

    private String icao;
    private String callsign;
    private Boolean milCallsign;
    private String detectedTimestamp;
    private String manufacture;
    private String model;
    private final SimpleDateFormat dateFormatSolr = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private Boolean newContact;
    // used for timestamping reports
    private long lastReport;
    // flag to run aquire aircraft model/type
    private Boolean needsAircraftModel;

    /**
     * Constructor
     *
     * Accepts dump1090 message string (.csv string) or undeclared
     * creates Aircraft data model object
     *
     * @param message - dump1090 port 30003 message
     */
    public NewContact(String message) {
        // execute merge operation
        this.merge(message);
    }

    public NewContact() { }

    /**
     * Merge dump1090 data message into object.
     *
     * @param message
     */
    public void merge(String message) {
        // parse the message into the fields
        merge(Arrays.asList(message.split(",")));
    }

    public void merge(NewContact newContact) {
        // verify objects are the same, otherwise exit
        assert this.getClass().getName().equals(newContact.getClass().getName());
        // iterate the fields and replace when not null
        for (Field field : this.getClass().getDeclaredFields()) {
            for (Field newField : newContact.getClass().getDeclaredFields()) {
                if (field.getName().equals(newField.getName())) {
                    try {
                        field.set(this, newField.get(newContact) == null ? field.get(this) : newField.get(newContact));
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
                    case 4:
                        this.icao = fieldVal;
                        break;
                    case 10:
                        this.callsign = fieldVal;
                        if (MilCallsignStringParse.isCallsignMil(this.callsign)) {
                            milCallsign = true;
                        }
                        break;
                }
            }
        }
        // set lastReport timestamp
        this.lastReport = System.currentTimeMillis();
        this.detectedTimestamp = dateFormatSolr.format(new Date());
    }

    /**
     * Testing shortcut
     *
     * Setups up some values that can be parsed from returned JSON payload
     */
    public void testInit() {
        this.callsign = "TESTY05";
        this.icao = "A00000";
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

    public String getIcao() {
        return icao;
    }

    public String getCallsign() {
        return callsign;
    }

    public String getManufacture() {
        return manufacture;
    }

    public String getModel() {
        return model;
    }

    public Boolean getMilCallsign() {
        return milCallsign;
    }

    public String getDetectedTimestamp() {
        return detectedTimestamp;
    }

    public long getLastReport() {
        return lastReport;
    }

    public Boolean isNewContact(){
        return newContact;
    }

    public boolean milContactTrue() {
        return (this.milCallsign != null && this.milCallsign);
    }

    public boolean hasCallsign() {
        return (this.callsign != null);
    }

    public boolean hasModelInformation() {
        return (!needsAircraftModel);  // OK, looks odd but returns True if *not* needing aircraft model info
    }

    /**
     * ** SETTERS ***
     */

    public void newContact(){
        this.newContact = true;
    }

    public void clearNewContact(){
        this.newContact = false;
    }

}

