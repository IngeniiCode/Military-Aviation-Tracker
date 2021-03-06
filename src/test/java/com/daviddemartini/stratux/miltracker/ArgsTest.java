package com.daviddemartini.stratux.miltracker;

import com.daviddemartini.stratux.miltracker.comms.stratux.TrafficWebSocket;
import com.daviddemartini.stratux.miltracker.util.Args;
import com.daviddemartini.stratux.miltracker.util.StringStuffer;

import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.daviddemartini.stratux.miltracker.util.MilCallsignStringParse;

public class ArgsTest {

    private static final String WS_PREFIX = "ws://";
    private static final String TEST_URI = "localhost/traffic";
    private static final String[] BAD_ARGS = StringStuffer.fill(new String[1], "");
    private static final String[] GOOD_WEBSOCKET_ARGS = StringStuffer.fill(new String[1], "--websocket=" + TEST_URI);

    @DisplayName("Test Argument Parser - Bad Arguments")
    @Test
    void testArgs_BadArguments() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Args clArgs = new Args(BAD_ARGS);
            clArgs.getSocketUrl();

        });

    }

    @DisplayName("Test Argument Parser - Good Arguments")
    @Test
    void testArgs_GoodArguments() throws Exception {

        Args clArgs = new Args(GOOD_WEBSOCKET_ARGS);
        String webSocketURI = clArgs.getSocketUrl();
        assertEquals(WS_PREFIX+TEST_URI, webSocketURI);


    }

}
