package com.daviddemartini.stratux.miltracker.comms.stratux;

import java.io.IOException;

public class NewContactFeed {

    private int feedSocketPort = 9101;


    /**
     * Constructor
     *
     * Expects a port to be passed, but will use the pre-configured
     * default if it is not
     *
     * @param port - integer port number to bind
     */
    public NewContactFeed(int port) throws IOException {
        // save the port
        this.feedSocketPort = port;

    }
    public NewContactFeed() throws IOException {
        new InRangeMilContactFeed(feedSocketPort);
    }


}
