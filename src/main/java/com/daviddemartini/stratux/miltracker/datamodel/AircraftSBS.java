package com.daviddemartini.stratux.miltracker.datamodel;


import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Aircraft object is based upon the MSG and other feed data
 * parsed from one of the following exemplar message strings
 *
 *
 * ****** MSG Transmission Examples *******
 *  MSG,1,145,256,7404F2,11267,2008/11/28,23:48:18.611,2008/11/28,23:53:19.161,RJA1118,,,,,,,,,,,
 *  MSG,2,496,603,400CB6,13168,2008/10/13,12:24:32.414,2008/10/13,12:28:52.074,,,0,76.4,258.3,54.05735,-4.38826,,,,,,0
 *  MSG,3,496,211,4CA2D6,10057,2008/11/28,14:53:50.594,2008/11/28,14:58:51.153,,37000,,,51.45735,-1.02826,,,0,0,0,0
 *  MSG,4,496,469,4CA767,27854,2010/02/19,17:58:13.039,2010/02/19,17:58:13.368,,,288.6,103.2,,,-832,,,,,
 *  MSG,5,496,329,394A65,27868,2010/02/19,17:58:12.644,2010/02/19,17:58:13.368,,10000,,,,,,,0,,0,0
 *  MSG,6,496,237,4CA215,27864,2010/02/19,17:58:12.846,2010/02/19,17:58:13.368,,33325,,,,,,0271,0,0,0,0
 *  MSG,7,496,742,51106E,27929,2011/03/06,07:57:36.523,2011/03/06,07:57:37.054,,3775,,,,,,,,,,0
 *  MSG,8,496,194,405F4E,27884,2010/02/19,17:58:13.244,2010/02/19,17:58:13.368,,,,,,,,,,,,0
 *
 * ****** Non-MSG Transmission Examples *******
 *  SEL,,496,2286,4CA4E5,27215,2010/02/19,18:06:07.710,2010/02/19,18:06:07.710,RYR1427
 *  ID,,496,7162,405637,27928,2010/02/19,18:06:07.115,2010/02/19,18:06:07.115,EZY691A
 *  AIR,,496,5906,400F01,27931,2010/02/19,18:06:07.128,2010/02/19,18:06:07.128
 *  STA,,5,179,400AE7,10103,2008/11/28,14:58:51.153,2008/11/28,14:58:51.153,RM
 *  CLK,,496,-1,,-1,2010/02/19,18:18:19.036,2010/02/19,18:18:19.036
 *
 */

public class AircraftSBS {

    private String transmissionType;
    private int msgType;
    private int sessionId;
    private int aircraftDbId;
    private String icao;
    private int flightDbId;
    private String messageReceiveDate;
    private String messageReceiveTime;
    private String messageLoggedDate;
    private String messageLoggedTime;
    // the following fields are not expected part of every message, and contain aircraft details
    private String callsign;
    private int altitude;
    private float speedGround;
    private float track;
    private double latitude;
    private double longitude;
    private int verticalRate;
    private int squawkInt;
    private boolean squawkChanged;
    private boolean emergency;
    private boolean spiIdent;
    private boolean onGround;

    /**
     * Constructor
     *
     *   Accepts dump1090 message string (.csv string) or undeclared
     *    creates Aircraft data model object
     *
     * @param message - dump1090 port 30003 message
     */
    public AircraftSBS(String message) {
        // execute merge operation
        this.merge(message);
    }
    public AircraftSBS() { }

    /**
     * Merge dump1090 data message into object.
     *
     * @param message
     */
    public void merge(String message){
        // parse the message into the fields
        merge(Arrays.asList(message.split(",")));
    }
    /**
     * Merge dump1090 pre-parsed list of fields
     *
     * @param parsedFields - pre-parsed list of Strings
     */
    public void merge(List<String> parsedFields){
        for (int fieldNum = 0; fieldNum < parsedFields.size(); fieldNum++){
            String fieldVal = parsedFields.get(fieldNum).trim();
            if(fieldVal != null && fieldVal.length() > 0) {
                switch (fieldNum) {
                    case 0:
                        this.transmissionType = fieldVal;
                        break;
                    case 1:
                        this.msgType = Integer.parseInt(fieldVal);
                        break;
                    case 2:
                        this.sessionId = Integer.parseInt(fieldVal);
                        break;
                    case 3:
                        this.aircraftDbId = Integer.parseInt(fieldVal);
                        break;
                    case 4:
                        this.icao = fieldVal;
                        break;
                    case 5:
                        this.flightDbId = Integer.parseInt(fieldVal);
                        break;
                    case 6:
                        this.messageReceiveDate = fieldVal;
                        break;
                    case 7:
                        this.messageReceiveTime = fieldVal;
                        break;
                    case 8:
                        this.messageLoggedDate = fieldVal;
                        break;
                    case 9:
                        this.messageLoggedTime = fieldVal;
                        break;
                    case 10:
                        this.callsign = fieldVal;
                        break;
                    case 11:
                        this.altitude = Integer.parseInt(fieldVal);
                        break;
                    case 12:
                        this.speedGround = Float.parseFloat(fieldVal);
                        break;
                    case 13:
                        this.track = Float.parseFloat(fieldVal);
                        break;
                    case 14:
                        this.latitude = Double.parseDouble(fieldVal);
                        break;
                    case 15:
                        this.longitude = Double.parseDouble(fieldVal);
                        break;
                    case 16:
                        this.verticalRate = Integer.parseInt(fieldVal);
                        break;
                    case 17:
                        this.squawkInt = Integer.parseInt(fieldVal);
                        break;
                    case 18:
                        this.squawkChanged = fieldVal.equals("1");
                        break;
                    case 19:
                        this.emergency = fieldVal.equals("1");
                        break;
                    case 20:
                        this.spiIdent = fieldVal.equals("1");
                        break;
                    case 21:
                        this.onGround = fieldVal.equals("1");
                        break;
                }
            }
        }
    }

    /**
     * Testing shortcut
     *
     * Setups up some values that can be parsed from returned JSON payload
     */
    public void testInit(){
        this.squawkInt = 1200;
        this.callsign = "TESTY05";
        this.icao = "A00000";
    }

    /**
     * Express self as a JSON string.
     * @return
     */
    public String toJSON() throws IOException {
        // convert this data object to JSON
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    public String getTransmissionType() {
        return transmissionType;
    }

    public int getMsgType() {
        return msgType;
    }

    public int getSessionId() {
        return sessionId;
    }

    public int getAircraftDbId() {
        return aircraftDbId;
    }

    public String getIcao() {
        return icao;
    }

    public int getFlightDbId() {
        return flightDbId;
    }

    public String getMessageReceiveDate() {
        return messageReceiveDate;
    }

    public String getMessageReceiveTime() {
        return messageReceiveTime;
    }

    public String getMessageLoggedDate() {
        return messageLoggedDate;
    }

    public String getMessageLoggedTime() {
        return messageLoggedTime;
    }

    public String getCallsign() {
        return callsign;
    }

    public long getAltitude() {
        return altitude;
    }

    public float getSpeedGround() {
        return speedGround;
    }

    public float getTrack() {
        return track;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getVerticalRate() {
        return verticalRate;
    }

    public int getSquawkInt() {
        return squawkInt;
    }

    public String getSquawk() {
        return String.format("%04d",squawkInt);
    }

    public boolean isSquawkChanged() {
        return squawkChanged;
    }

    public boolean isEmergency() {
        return emergency;
    }

    public boolean isSpiIdent() {
        return spiIdent;
    }

    public boolean isOnGround() {
        return onGround;
    }
}
