package com.daviddemartini.stratux.miltracker;
import com.daviddemartini.stratux.miltracker.util.MilCallsignStringParse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.daviddemartini.stratux.miltracker.comms.stratux.TrafficWebSocket;

import java.net.UnknownHostException;

public class TrafficWebSocketTest {

    private static final String BAD_URL = "1.2.3.4/nowhere";
    private static final int BAD_PORT = -9999999;

    @DisplayName("Test Websocket Bad URI provided")
    @Test
    void testBadWebsocket_URI() {

        Assertions.assertThrows(UnknownHostException.class, () -> {
            TrafficWebSocket badSocket = new TrafficWebSocket(BAD_URL);
        });

    }

    @DisplayName("Test Websocket Bad URI & port provided")
    @Test
    void testBadWebsocket_URI_and_Port() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            TrafficWebSocket badSocket = new TrafficWebSocket(BAD_URL,BAD_PORT);
        });

    }



}
