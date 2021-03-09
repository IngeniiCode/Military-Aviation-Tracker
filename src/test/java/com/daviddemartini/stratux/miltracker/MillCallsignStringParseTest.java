package com.daviddemartini.stratux.miltracker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.daviddemartini.stratux.miltracker.util.MilCallsignStringParse;
public class MillCallsignStringParseTest {

    // create simple test case.
    private MilCallsignStringParse milParse = new MilCallsignStringParse();

    @DisplayName("Test Mil Callsign Message Parser - Detect Military Callsign")
    @Test
    void testGetMillCallsignMessageParse_Detect() {
        System.out.println("\tMil Callsign Message Parser - Detect Military Callsign");

        String testMessage = "{\"Icao_addr\":10868328,\"DF\":17,\"CA\":5,\"TypeCode\":4,\"SubtypeCode\":3,\"SBS_MsgType\":1,\"SignalLevel\":0.000482,\"Tail\":\"BONES05 \",\"Squawk\":null,\"Emitter_category\":3,\"OnGround\":false,\"Lat\":null,\"Lng\":null,\"Position_valid\":false,\"NACp\":null,\"Alt\":null,\"AltIsGNSS\":false,\"GnssDiffFromBaroAlt\":null,\"Vvel\":null,\"Speed_valid\":false,\"Speed\":null,\"Track\":null,\"Timestamp\":\"2021-03-05T18:41:00.057Z\"}";

        assertEquals(testMessage, milParse.parseMessageCSV(testMessage));
    }

    @DisplayName("Test Mil Callsign Message Parser - Reject Commercial Callsign")
    @Test
    void testGetMillCallsignMessageParse_Reject() {
        System.out.println("\tMil Callsign Message Parser - Reject Commercial Callsign");

        String testMessage = "{\"Icao_addr\":10868328,\"DF\":17,\"CA\":5,\"TypeCode\":4,\"SubtypeCode\":3,\"SBS_MsgType\":1,\"SignalLevel\":0.000482,\"Tail\":\"UAL1074 \",\"Squawk\":null,\"Emitter_category\":3,\"OnGround\":false,\"Lat\":null,\"Lng\":null,\"Position_valid\":false,\"NACp\":null,\"Alt\":null,\"AltIsGNSS\":false,\"GnssDiffFromBaroAlt\":null,\"Vvel\":null,\"Speed_valid\":false,\"Speed\":null,\"Track\":null,\"Timestamp\":\"2021-03-05T18:41:00.057Z\"}";

        assertEquals(null, milParse.parseMessageCSV(testMessage));
    }

    @DisplayName("Test Mil Conventional Callsign String Parser")
    @Test
    void testGetMillCallsignStringParse_Conventional() {
        System.out.println("\tMil Conventional Callsign String Parser");

        // this should not return as military
        assertFalse(milParse.isCallsignMil("SKW3192"));
        // these should return as military
        assertTrue(milParse.isCallsignMil("SCARY01"));
        assertTrue(milParse.isCallsignMil("GROOV01 "));
        assertTrue(milParse.isCallsignMil("OINK5 "));

    }

    @DisplayName("Test Mil Unconventional Callsign String Parser")
    @Test
    void testGetMillCallsignStringParse_Unconventional() {
        System.out.println("\tMil Unconventional Callsign String Parser" );

        // example of a non-callsign flight ID
        assertTrue(milParse.isCallsignMil("R08885  "));
        // example of a hull registration flight ID
        assertTrue(milParse.isCallsignMil("_08-5691"));

    }

}
