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
    private static double maxRange = 0; // 0 == unlimited range
    private static Args clArgs;

    public static void main (String[] args){
        // process the CL args..
        try {
            // Process args from commandline
            clArgs = new Args(args);

            // Setup stream reader
            SBSTrafficSocket sbsTrafficSocket = new SBSTrafficSocket(clArgs.getSocketHost(), clArgs.getSocketPort());

            if(clArgs.hasPosition()){
                maxRange = (clArgs.getMaxRange() > 0) ? clArgs.getMaxRange() : maxRange;
                if(!clArgs.hasQuiet()) {
                    System.out.printf("Fixed Position Set.  Only %scontacts with position %sare displayed\n\n",
                            (clArgs.hasMilOnly()) ? "Military " : "",
                            (maxRange > 0) ? String.format("within %.1f miles ",maxRange): ""
                    );
                }
                fixedPositionLatitude = clArgs.getPositionLatitude();
                fixedPositionLongitude = clArgs.getPositionLongitude();
                distanceBearing = new DistanceBearing(fixedPositionLatitude,fixedPositionLongitude);
            }

            // setup the publishing ports -- BLOCKING
            //InRangeFeed rangeFeed = new InRangeFeed(9001);

            // process traffic feed from  sbsTrafficSocket
            processMessages(AirTraffic, sbsTrafficSocket);

        }
        catch (Exception e) {
            System.err.printf("\n** Fatal Error -- %s\n",e.toString());
            e.printStackTrace();
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
                    // only message with callsign
                case 2:
                    // has ground speed
                case 3:
                    if(distanceBearing != null) {
                        distanceBearing.calculate(contact.getLatitude(),contact.getLongitude());
                        contact.setBearing(distanceBearing.getContactAzimuth());
                        contact.setDistance(distanceBearing.getContactDistance());
                        // if contact is within the defined range, set displayContact flag
                        if(maxRange > 0) {
                            if(distanceBearing.getContactDistance() <= maxRange){
                                displayContact = true;
                            }
                        }
                        else {
                            displayContact = true;
                        }
                    }
                    break;
                case 4:
                    // has ground speed
                    break;
                case 6:
                    // contains the sqwak code.
                default:
                    // something?
            }

            // Get the ICAO Hex present in every message
            String icao = contact.getIcao();

            // check to see if this is a known or new contact
            if(airTraffic.containsKey(icao)){
                airTraffic.get(icao).merge(contact);
            }
            else {
                airTraffic.put(icao,contact);
                // it not operating in quiet mode, announce the new contact
                if(!clArgs.hasQuiet()) {
                    System.out.printf("\tNew Contact - '%s'\n", icao);
                }
            }

            // decide if contact should be displayed or not
            if(displayContact){
                if(clArgs.hasMilOnly()){
                    if(airTraffic.get(icao).isMilCallsign() != null && airTraffic.get(icao).isMilCallsign().booleanValue()){
                        System.out.printf("\t\t%s\n", airTraffic.get(icao).announceContactTerse());
                    }
                }
                else {
                    System.out.printf("\t\t%s\n", airTraffic.get(icao).announceContactTerse());
                }
            }
        }

        // done, close socket
        sbsTrafficSocket.getSocket().close();

    }
}
