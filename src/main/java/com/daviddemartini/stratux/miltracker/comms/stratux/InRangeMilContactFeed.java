package com.daviddemartini.stratux.miltracker.comms.stratux;

import java.io.IOException;

public class InRangeMilContactFeed {

    private int feedSocketPort = 9104;


    /**
     * Constructor
     *
     * Expects a port to be passed, but will use the pre-configured
     * default if it is not
     *
     * @param port - integer port number to bind
     */
    public InRangeMilContactFeed(int port) throws IOException {
        // save the port
        this.feedSocketPort = port;

    }
    public InRangeMilContactFeed() throws IOException {
        new InRangeMilContactFeed(feedSocketPort);
    }

}
