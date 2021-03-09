package com.daviddemartini.stratux.miltracker;

import com.daviddemartini.stratux.miltracker.util.Args;
import com.daviddemartini.stratux.miltracker.util.StringStuffer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArgsTest {

    private static final String TEST_HOST = "localhost";
    private static final String[] BAD_ARGS = StringStuffer.fill(new String[1], "");
    private static final String[] GOOD_WEBSOCKET_ARGS = StringStuffer.fill(new String[1], "--host=" + TEST_HOST);

    @DisplayName("Test Argument Parser - Bad Arguments")
    @Test
    void testArgs_BadArguments() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Args clArgs = new Args(BAD_ARGS);
            clArgs.getSocketHost();
        });

    }

    @DisplayName("Test Argument Parser - Good Arguments")
    @Test
    void testArgs_GoodArguments() throws Exception {

        Args clArgs = new Args(GOOD_WEBSOCKET_ARGS);
        String webSocketURI = clArgs.getSocketHost();
        assertEquals(TEST_HOST, webSocketURI);

    }

}
