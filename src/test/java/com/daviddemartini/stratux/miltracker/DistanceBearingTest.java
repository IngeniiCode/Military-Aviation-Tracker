package com.daviddemartini.stratux.miltracker;

import com.daviddemartini.stratux.miltracker.geolocation.DistanceBearing;

import com.daviddemartini.stratux.miltracker.util.StringToJson;
import org.codehaus.jackson.JsonNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DistanceBearingTest {

    private static final double fixedLatitude_1 =  29.91401;
    private static final double fixedLongitude_1 = -98.20301;
    private static final double fixedLatitude_2 =  0;
    private static final double fixedLongitude_2 = fixedLongitude_1;

    private static final double contactLatutude_1 = 30.83667;
    private static final double contactLongitude_1 = -99.02763;

    private static final double contactLatutude_2 = 30.91401;
    private static final double contactLongitude_2 = fixedLongitude_1;

    private static final double contactLatutude_3 = fixedLatitude_2;
    private static final double contactLongitude_3 = -97.20301;

    private static final double contactLatutude_4 = 28.9140;
    private static final double contactLongitude_4 = fixedLongitude_1;

    private static final double contactLatutude_5 = fixedLatitude_2;
    private static final double contactLongitude_5 = -99.20301;


    @DisplayName("DistanceBearing - Location 1 Object Test ")
    @Test
    void  test_DistanceBearing_Location_1_Object() throws IOException {
        System.out.println("\tDistanceBearing - Location 1 Object Test");

        float correctContactDistance = (float) 81.09; // 81.09
        float correctContactBearing = (float) 310.73; // 322.96 from FCC calculator

        // setup calculator object and perform preliminary calculation
        DistanceBearing distanceBearing = new DistanceBearing(fixedLatitude_1, fixedLongitude_1);
        distanceBearing.calculate(contactLatutude_1,contactLongitude_1);

        // assert!
        assertEquals(correctContactDistance,distanceBearing.getContactDistance());
        assertEquals(correctContactBearing,distanceBearing.getContactAzimuth());
        assertTrue(distanceBearing.isValid());
    }

    @DisplayName("DistanceBearing - Location 1 Json Encoding Test ")
    @Test
    void  test_DistanceBearing_Location_1_Json() throws IOException {
        System.out.println("\tDistanceBearing - Location 1 Json Encoding Test");

        float correctContactDistance = (float) 81.09; // 81.09
        float correctContactBearing = (float) 310.73; // 322.96 from FCC calculator

        // setup calculator object and perform preliminary calculation
        DistanceBearing distanceBearing = new DistanceBearing(fixedLatitude_1, fixedLongitude_1);
        distanceBearing.calculate(contactLatutude_1,contactLongitude_1);
        // test Json encoding
        String jsonString = distanceBearing.toJSON();
        JsonNode jsonNode = StringToJson.parse(jsonString);

        // assert!
        assertEquals(correctContactDistance,Float.parseFloat(jsonNode.get("contactDistance").toString()));
        assertEquals(correctContactBearing,Float.parseFloat(jsonNode.get("contactAzimuth").toString()));
        assertTrue(jsonNode.get("valid").asBoolean());
    }

    @DisplayName("DistanceBearing - 0 degree Azimuth Json Encoding Test ")
    @Test
    void  test_DistanceBearing_Location_0_Azimuth_Json() throws IOException {
        System.out.println("\tDistanceBearing - 0 degree Azimuth Json Encoding Test");

        float correctContactDistance = (float) 69.67; // 69.67
        float correctContactBearing = (float) 0; // 0

        // setup calculator object and perform preliminary calculation
        DistanceBearing distanceBearing = new DistanceBearing(fixedLatitude_1, fixedLongitude_1);
        distanceBearing.calculate(contactLatutude_2,contactLongitude_2);
        // test Json encoding
        String jsonString = distanceBearing.toJSON();
        JsonNode jsonNode = StringToJson.parse(jsonString);

        // assert!
        assertEquals(correctContactDistance,Float.parseFloat(jsonNode.get("contactDistance").toString()));
        assertEquals(correctContactBearing,Float.parseFloat(jsonNode.get("contactAzimuth").toString()));
        assertTrue(jsonNode.get("valid").asBoolean());
    }

    @DisplayName("DistanceBearing - 90 degree Azimuth Json Encoding Test ")
    @Test
    void  test_DistanceBearing_Location_90_Azimuth_Json() throws IOException {
        System.out.println("\tDistanceBearing - 90 degree Azimuth Json Encoding Test");

        float correctContactDistance = (float) 6792.86; // 6792.86
        float correctContactBearing = (float) 115.02; // 90 expected as correct result

        // setup calculator object and perform preliminary calculation
        DistanceBearing distanceBearing = new DistanceBearing(fixedLongitude_2, fixedLongitude_2);
        distanceBearing.calculate(contactLatutude_3,contactLongitude_3);
        // test Json encoding
        String jsonString = distanceBearing.toJSON();
        JsonNode jsonNode = StringToJson.parse(jsonString);

        // assert!
        assertEquals(correctContactDistance,Float.parseFloat(jsonNode.get("contactDistance").toString()));
        assertEquals(correctContactBearing,Float.parseFloat(jsonNode.get("contactAzimuth").toString()));
        assertTrue(jsonNode.get("valid").asBoolean());
    }

    @DisplayName("DistanceBearing - 180 degree Azimuth Json Encoding Test ")
    @Test
    void  test_DistanceBearing_Location_180_Azimuth_Json() throws IOException {
        System.out.println("\tDistanceBearing - 180 degree Azimuth Json Encoding Test");

        float correctContactDistance = (float) 69.67; // 69.67
        float correctContactBearing = (float) 180; // 180

        // setup calculator object and perform preliminary calculation
        DistanceBearing distanceBearing = new DistanceBearing(fixedLatitude_1, fixedLongitude_1);
        distanceBearing.calculate(contactLatutude_4,contactLongitude_4);
        // test Json encoding
        String jsonString = distanceBearing.toJSON();
        JsonNode jsonNode = StringToJson.parse(jsonString);

        // assert!
        assertEquals(correctContactDistance,Float.parseFloat(jsonNode.get("contactDistance").toString()));
        assertEquals(correctContactBearing,Float.parseFloat(jsonNode.get("contactAzimuth").toString()));
        assertTrue(jsonNode.get("valid").asBoolean());
    }

    @DisplayName("DistanceBearing - 270 degree Azimuth Json Encoding Test ")
    @Test
    void  test_DistanceBearing_Location_270_Azimuth_Json() throws IOException {
        System.out.println("\tDistanceBearing - 270 degree Azimuth Json Encoding Test");

        float correctContactDistance = (float) 6792.86; // 6792.86
        float correctContactBearing = (float) 244.98; // 270 expected as correct result

        // setup calculator object and perform preliminary calculation
        DistanceBearing distanceBearing = new DistanceBearing(fixedLongitude_2, fixedLongitude_2);
        distanceBearing.calculate(contactLatutude_5,contactLongitude_5);
        // test Json encoding
        String jsonString = distanceBearing.toJSON();
        JsonNode jsonNode = StringToJson.parse(jsonString);

        // assert!
        assertEquals(correctContactDistance,Float.parseFloat(jsonNode.get("contactDistance").toString()));
        assertEquals(correctContactBearing,Float.parseFloat(jsonNode.get("contactAzimuth").toString()));
        assertTrue(jsonNode.get("valid").asBoolean());
    }

}
