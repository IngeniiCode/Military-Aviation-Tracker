package com.daviddemartini.stratux.miltracker.comms.stratux;

import java.io.IOException;

public class InRangeFeed {

    private int feedSocketPort = 9102;


    /**
     * Constructor
     *
     * Expects a port to be passed, but will use the pre-configured
     * default if it is not
     *
     * @param port - integer port number to bind
     */
    public InRangeFeed(int port) throws IOException {
        // save the port
        this.feedSocketPort = port;
    }

    public InRangeFeed() throws IOException {
        new InRangeFeed(feedSocketPort);
    }

}
