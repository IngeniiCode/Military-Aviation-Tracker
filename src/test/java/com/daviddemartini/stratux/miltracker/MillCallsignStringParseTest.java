package com.daviddemartini.stratux.miltracker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.daviddemartini.stratux.miltracker.util.MilCallsignStringParse;
public class MillCallsignStringParseTest {

    // create simple test case.

    @DisplayName("Test Mil Callsign Message Parser")
    @Test
    void testGetMillCallsignStringParse_1() {
        String testMessage = "{\"Icao_addr\":10868328,\"DF\":17,\"CA\":5,\"TypeCode\":4,\"SubtypeCode\":3,\"SBS_MsgType\":1,\"SignalLevel\":0.000482,\"Tail\":\"UAL1074 \",\"Squawk\":null,\"Emitter_category\":3,\"OnGround\":false,\"Lat\":null,\"Lng\":null,\"Position_valid\":false,\"NACp\":null,\"Alt\":null,\"AltIsGNSS\":false,\"GnssDiffFromBaroAlt\":null,\"Vvel\":null,\"Speed_valid\":false,\"Speed\":null,\"Track\":null,\"Timestamp\":\"2021-03-05T18:41:00.057Z\"}";
        MilCallsignStringParse milParse = new MilCallsignStringParse();

        assertEquals(testMessage, milParse.Parse(testMessage));
    }


}
