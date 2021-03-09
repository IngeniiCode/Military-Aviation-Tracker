package com.daviddemartini.stratux.miltracker;

import com.daviddemartini.stratux.miltracker.util.Args;
import com.daviddemartini.stratux.miltracker.util.StringStuffer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArgsTest {

    private static final String TEST_HOST = "localhost";
    private static final int TEST_PORT = 30003;
    private static final double TEST_LAT = 29.91401;
    private static final double TEST_LON = -98.20301;
    private static final String[] BAD_ARGS = StringStuffer.fill(new String[1], "");
    //private static String[] GOOD_WEBSOCKET_ARGS = new String[4];

    @BeforeAll
    static void firstOperations(){
        // pre test setup here, if required
    }

    @DisplayName("Test Argument Parser - Bad Arguments")
    @Test
    void testArgs_BadArguments() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Args clArgs = new Args(BAD_ARGS);
            clArgs.getSocketHost();
        });

    }

    @DisplayName("Test Argument Parser - Minimum Arguments")
    @Test
    void testArgs_MinimumArguments() throws Exception {
        System.out.println("\tArgument Parser - Minimum Arguments");
        // setup
        String[] GOOD_WEBSOCKET_ARGS = new String[2];
        GOOD_WEBSOCKET_ARGS[0] = "--host=" + TEST_HOST;
        GOOD_WEBSOCKET_ARGS[1] = "--port=" + TEST_PORT;
        // evaluate
        Args clArgs = new Args(GOOD_WEBSOCKET_ARGS);
        assertEquals(TEST_HOST, clArgs.getSocketHost());
        assertEquals(TEST_PORT, clArgs.getSocketPort());

    }

    @DisplayName("Test Argument Parser - All Arguments")
    @Test
    void testArgs_AllArguments() throws Exception {
        System.out.println("\tArgument Parser - Maximum Arguments");
        // setup
        String[] GOOD_WEBSOCKET_ARGS = new String[4];
        GOOD_WEBSOCKET_ARGS[0] = "--host=" + TEST_HOST;
        GOOD_WEBSOCKET_ARGS[1] = "--port=" + TEST_PORT;
        GOOD_WEBSOCKET_ARGS[2] = "--lat=" + TEST_LAT;
        GOOD_WEBSOCKET_ARGS[3] = "--lon=" + TEST_LON;

        // evaluate
        Args clArgs = new Args(GOOD_WEBSOCKET_ARGS);
        assertEquals(TEST_HOST, clArgs.getSocketHost());
        assertEquals(TEST_PORT, clArgs.getSocketPort());
        assertEquals(TEST_LAT, clArgs.getPositionLatitude());
        assertEquals(TEST_LON, clArgs.getPositionLongitude());

    }



}
