package com.daviddemartini.stratux.miltracker;

import com.daviddemartini.stratux.miltracker.comms.stratux.TrafficWebSocket;
import com.daviddemartini.stratux.miltracker.datamodel.AircraftSBS;
import com.daviddemartini.stratux.miltracker.geolocation.DistanceBearing;
import com.daviddemartini.stratux.miltracker.util.Args;
import com.daviddemartini.stratux.miltracker.util.StringStuffer;

import com.daviddemartini.stratux.miltracker.util.StringToJson;
import org.apache.commons.cli.ParseException;
import org.codehaus.jackson.JsonNode;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DistanceBearingTest {

    private static final double fixedLatitude_1 =  29.89401;
    private static final double fixedLongitude_1 = -98.20301;
    private static final double contactLatutude_1 = 30.83667;
    private static final double contactLongitude_1 = -99.02763;
    private static final double fixedLatitude_2 =  39.51111;
    private static final double fixedLongitude_2 = -102.41111;

    @DisplayName("DistanceBearing - Location 1 Object Test ")
    @Test
    void  test_DistanceBearing_Location_1_Object() throws IOException {
        System.out.println("\tDistanceBearing - Location 1 Object Test");

        float correctPoleDistance = (float) 4157.87; // 4157.87
        float correctContactDistance = (float) 82.19; // 82.19
        float correctContactBearing = (float) 323.16; // 323.16

        // setup calculator object and perform preliminary calculation
        DistanceBearing distanceBearing = new DistanceBearing(fixedLatitude_1, fixedLongitude_1);
        distanceBearing.calculate(contactLatutude_1,contactLongitude_1);

        // assert!
        assertEquals(correctPoleDistance,distanceBearing.getDistanceFromPole());
        assertEquals(correctContactDistance,distanceBearing.getDistance());
        //assertEquals(correctAzimuth,Double.parseDouble(jsonNode.get("bearing").toString()));
        assertTrue(distanceBearing.isValid());

    }

    @DisplayName("DistanceBearing - Location 1 Json Encoding Test ")
    @Test
    void  test_DistanceBearing_Location_1_Json() throws IOException {
        System.out.println("\tDistanceBearing - Location 1 Json Encoding Test");

        float correctPoleDistance = (float) 4157.87; // 4157.87
        float correctContactDistance = (float) 82.19; // 82.19
        float correctContactBearing = (float) 323.16; // 323.16

        // setup calculator object and perform preliminary calculation
        DistanceBearing distanceBearing = new DistanceBearing(fixedLatitude_1, fixedLongitude_1);
        distanceBearing.calculate(contactLatutude_1,contactLongitude_1);
        // test Json encoding
        String jsonString = distanceBearing.toJSON();
        JsonNode jsonNode = StringToJson.parse(jsonString);

        // assert!
        assertEquals(correctPoleDistance,Float.parseFloat(jsonNode.get("distanceFromPole").toString()));
        assertEquals(correctContactDistance,Float.parseFloat(jsonNode.get("distance").toString()));
        //assertEquals(correctAzimuth,Float.parseFloat(jsonNode.get("bearing").toString()));
        assertTrue(jsonNode.get("valid").asBoolean());

    }


}
