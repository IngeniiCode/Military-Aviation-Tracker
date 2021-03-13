package com.daviddemartini.avtracker.services;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.daviddemartini.avtracker.services.comms.stratux.SBSTrafficSocket;

import java.net.UnknownHostException;

public class SBSTrafficSocketTest {

    private static final String BAD_URL = "1.2.3.4/nowhere";
    private static final int BAD_PORT = -9999999;

    @DisplayName("Test Websocket Bad URI provided")
    @Test
    void testBadWebsocket_URI() {
        System.out.println("\tWebsocket Bad URI provided");
        Assertions.assertThrows(UnknownHostException.class, () -> {
            SBSTrafficSocket badSocket = new SBSTrafficSocket(BAD_URL);
        });

    }

    @DisplayName("Test Websocket Bad URI & port provided")
    @Test
    void testBadWebsocket_URI_and_Port() {
        System.out.println("\tWebsocket Bad URI & port provided");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            SBSTrafficSocket badSocket = new SBSTrafficSocket(BAD_URL,BAD_PORT);
        });

    }



}
