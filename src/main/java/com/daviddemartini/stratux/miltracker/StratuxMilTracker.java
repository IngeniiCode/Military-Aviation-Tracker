package com.daviddemartini.stratux.miltracker;

import com.daviddemartini.stratux.miltracker.comms.stratux.SBSTrafficSocket;
import com.daviddemartini.stratux.miltracker.geolocation.DistanceBearing;
import com.daviddemartini.stratux.miltracker.util.Args;
import com.daviddemartini.stratux.miltracker.datamodel.AircraftSBS;
import com.daviddemartini.stratux.miltracker.util.MilCallsignStringParse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class StratuxMilTracker {

    // need a hash map of ICOA ID -> SBS Object
    private static Map<String,AircraftSBS> AirTraffic = new HashMap<>();
    private static DistanceBearing distanceBearing = null;
    private static double fixedPositionLatitude;
    private static double fixedPositionLongitude;
    private static double maxRange = 5;

    public static void main (String[] args){
        // process the CL args..
        try {
            // Process args from commandline
            Args clArgs = new Args(args);

            // Setup stream reader
            SBSTrafficSocket sbsTrafficSocket = new SBSTrafficSocket(clArgs.getSocketHost(), clArgs.getSocketPort());

            if(clArgs.hasPosition()){
                maxRange = (clArgs.getMaxRange() > 0) ? clArgs.getMaxRange() : maxRange;
                System.out.printf("Fixed Position Set.  Only contacts within %.1f miles are displayed\n\n",maxRange);
                fixedPositionLatitude = clArgs.getPositionLatitude();
                fixedPositionLongitude = clArgs.getPositionLongitude();
                distanceBearing = new DistanceBearing(fixedPositionLatitude,fixedPositionLongitude);
            }

            // process traffic feed from  sbsTrafficSocket
            processMessages(AirTraffic, sbsTrafficSocket);

        }
        catch (Exception e) {
            System.err.printf("\n** Fatal Error -- %s\n",e.toString());
            System.exit(9);
        }

    }


    /**
     * Read simple text message strings from the port connection stream
     *
     * Parse into object, check ICAO to see if it had been seen before, and if not, add to in-mem dataset, otherwise
     * merge messages into a unified model -- at some point the model would be complete enough to express a position,
     * callsign and bearing.
     *
     * @param airTraffic
     * @param sbsTrafficSocket
     * @throws IOException
     */
    private static void processMessages(Map<String,AircraftSBS> airTraffic, SBSTrafficSocket sbsTrafficSocket) throws IOException {

        BufferedReader bis = new BufferedReader(new InputStreamReader(sbsTrafficSocket.getSocket().getInputStream()));
        String dump1090Message;
        while ((dump1090Message = bis.readLine()) != null) {
            AircraftSBS contact = new AircraftSBS(dump1090Message);
            boolean displayContact = false;
            // perform special processing based on message type
            switch(contact.getMsgType()){
                case 1:
                    contact.setMilCallsign(MilCallsignStringParse.isCallsignMil(contact.getCallsign()));
                    break;
                case 2:
                case 3:
                    if(distanceBearing != null) {
                        distanceBearing.calculate(contact.getLatitude(),contact.getLongitude());
                        contact.setBearing(distanceBearing.getContactAzimuth());
                        contact.setDistance(distanceBearing.getContactDistance());
                        // if contact is within the defined range, announce target
                        if(distanceBearing.getContactDistance() <= maxRange){
                            displayContact = true;
                        }
                    }
                    break;
                default:
                    // something?
            }

            // Get the ICAO Hex persent in every message
            String icao = contact.getIcao();
            // check to see if this is a known or new contact
            if(airTraffic.containsKey(icao)){
                airTraffic.get(icao).merge(contact);
            }
            else {
                System.out.printf("\tNew Contact - '%s'\n",icao);
                airTraffic.put(icao,contact);
            }

            // decide if contact should be displayed or not
            if(displayContact){
                System.out.printf("\t\t%s\n",airTraffic.get(icao).announceContactTerse());
            }
        }
        // done, close socket
        sbsTrafficSocket.getSocket().close();

    }
}
