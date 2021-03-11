package com.daviddemartini.stratux.miltracker.comms.stratux;

import com.daviddemartini.stratux.miltracker.datamodel.AircraftSBS;

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

    public void publish(AircraftSBS contact){
        // express new contact in JSON with any known data.
        try {
            System.out.println("NEW CONTACT:\t" + contact.toJSONLite());
        }
        catch (Exception e) {
            System.err.println("NEW CONTACT ERROR");
            e.printStackTrace();
        }
    }


}
