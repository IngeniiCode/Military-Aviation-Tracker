package com.daviddemartini.stratux.miltracker;

import com.daviddemartini.stratux.miltracker.comms.stratux.MutliThreadChannelSocket;
import com.daviddemartini.stratux.miltracker.comms.stratux.SBSTrafficSocket;
import com.daviddemartini.stratux.miltracker.datamodel.AircraftSBS;
import com.daviddemartini.stratux.miltracker.geolocation.DistanceBearing;
import com.daviddemartini.stratux.miltracker.util.Args;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class StratuxMilTracker {

    // need a hash map of ICOA ID -> SBS Object
    private static final Map<String, AircraftSBS> AirTraffic = new HashMap<>();
    private static DistanceBearing distanceBearing = null;
    private static double fixedPositionLatitude;
    private static double fixedPositionLongitude;
    private static double maxRange = 0; // 0 == unlimited range
    private static Args clArgs;
    private static MutliThreadChannelSocket PubNewContact;
    private static MutliThreadChannelSocket PubMilContact;
    private static MutliThreadChannelSocket PubInRangeContact;
    private static MutliThreadChannelSocket PubMilInRangeContact;
    private static MutliThreadChannelSocket PubEveryContact;

    public static void main(String[] args) {
        // process the CL args..
        try {
            // Process args from commandline
            clArgs = new Args(args);

            // Setup stream reader
            SBSTrafficSocket sbsTrafficSocket = new SBSTrafficSocket(clArgs.getDump1090Hostname(), clArgs.getDump1090Port());

            if (clArgs.hasPosition()) {
                maxRange = (clArgs.getMaxDetectionRange() > 0) ? clArgs.getMaxDetectionRange() : maxRange;
                if (!clArgs.hasQuiet()) {
                    System.out.printf("Fixed Position Set.  Only %scontacts with position %sare displayed\n\n",
                            (clArgs.hasMilOnly()) ? "Military " : "",
                            (maxRange > 0) ? String.format("within %.1f miles ", maxRange) : ""
                    );
                }
                fixedPositionLatitude = clArgs.getFixedPositionLatitude();
                fixedPositionLongitude = clArgs.getFixedPositionLongitude();
                distanceBearing = new DistanceBearing(fixedPositionLatitude, fixedPositionLongitude);
            }

            // setup the publishing ports
            System.out.println("Starting Feed Publishers");
            PubNewContact = new MutliThreadChannelSocket(clArgs.getPublisherHostname(),9101);
            PubInRangeContact = new MutliThreadChannelSocket(clArgs.getPublisherHostname(),9102);
            PubMilContact = new MutliThreadChannelSocket(clArgs.getPublisherHostname(),9103);
            PubMilInRangeContact = new MutliThreadChannelSocket(clArgs.getPublisherHostname(),9104);
            PubEveryContact = new MutliThreadChannelSocket(clArgs.getPublisherHostname(),9105);

            // process traffic feed from  sbsTrafficSocket
            processMessages(AirTraffic, sbsTrafficSocket);

        } catch (Exception e) {
            System.err.printf("\n** Fatal Error -- %s\n", e.toString());
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
    private static void processMessages(Map<String, AircraftSBS> airTraffic, SBSTrafficSocket sbsTrafficSocket) throws IOException {

        BufferedReader bis = new BufferedReader(new InputStreamReader(sbsTrafficSocket.getSocket().getInputStream()));
        String dump1090Message;

        while ((dump1090Message = bis.readLine()) != null) {

            // init flag to determine writes to <STDOUT>
            boolean publishContact = false;
            boolean publishInRageContact = false;

            // convert the CSV message string into an AircraftSBS message object
            AircraftSBS contact = new AircraftSBS(dump1090Message);

            // perform special processing based on message type
            switch (contact.getMsgType()) {
                case 1:
                    // only message with callsign
                    break;
                case 2:
                    // has ground speed
                case 3:
                    if (distanceBearing != null) {
                        distanceBearing.calculate(contact.getLatitude(), contact.getLongitude());
                        contact.setBearing(distanceBearing.getContactAzimuth());
                        contact.setDistance(distanceBearing.getContactDistance());
                        // if contact is within the defined range, set displayContact flag
                        if (maxRange > 0) {
                            if (distanceBearing.getContactDistance() <= maxRange) {
                                publishInRageContact = true;
                            }
                        }
                        publishContact = true;
                    }
                    break;
                case 4:
                    // has ground speed
                    break;
                case 6:
                    // contains the squawk code.
                    break;
                case 7:
                case 8:
                default:
                    // TBD
            }

            // Get the ICAO Hex present in every message
            String icao = contact.getIcao();

            // check to see if this is a known or new contact
            if (airTraffic.containsKey(icao)) {
                airTraffic.get(icao).merge(contact);
                // don't announce anything that doesn't yet have a callsign, it's just not interesting
                if (airTraffic.get(icao).getCallsign() == null) {
                    publishContact = false;
                }
            } else {
                airTraffic.put(icao, contact);
                // it not operating in quiet mode, announce the new contact
                if (!clArgs.hasQuiet()) {
                    if (PubNewContact.publishContact(contact)) {
                        System.out.println("Published New Contact: " + icao);
                    }
                }
            }

            // decide if contact should be displayed or not
            if (publishContact) {
                PubEveryContact.publishContact(airTraffic.get(icao));
                if (airTraffic.get(icao).isMilCallsign() != null && airTraffic.get(icao).isMilCallsign().booleanValue()) {
                    PubMilContact.publishContact(airTraffic.get(icao));
                    if(publishInRageContact) {
                        PubMilInRangeContact.publishContact(airTraffic.get(icao));
                    }
                    if (clArgs.hasMilOnly()) {
                        Announce(airTraffic.get(icao));
                    }
                }
                if(publishInRageContact){
                    PubInRangeContact.publishContact(airTraffic.get(icao));
                }
            } else {
                Announce(airTraffic.get(icao));
            }
        }

        // done, close socket
        sbsTrafficSocket.getSocket().close();

    }

    private static void Announce(AircraftSBS contact){
        if(contact.getCallsign() != null){
            System.out.printf("\t\t%s\n", contact.announceContactTerse());
        }
    }

}
