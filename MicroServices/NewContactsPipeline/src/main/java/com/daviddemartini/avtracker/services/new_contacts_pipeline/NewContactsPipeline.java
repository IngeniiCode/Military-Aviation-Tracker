package com.daviddemartini.avtracker.services.new_contacts_pipeline;

import com.daviddemartini.avtracker.services.new_contacts_pipeline.comms.solr.ContactIndexer;
import com.daviddemartini.avtracker.services.new_contacts_pipeline.comms.stratux.SBSTrafficSocket;
import com.daviddemartini.avtracker.services.new_contacts_pipeline.util.Args;
import com.daviddemartini.avtracker.services.new_contacts_pipeline.datamodel.NewContact;
import com.daviddemartini.avtracker.services.new_contacts_pipeline.datamodel.CacheManager;
import org.apache.commons.cli.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class NewContactsPipeline {

    // need a hash map of ICOA ID -> SBS Object
    private static final Map<String, NewContact> ActiveContacts = new HashMap<>();
    private static boolean debug = false;
    private static int resolution = 10;
    private static double fixedPositionLatitude;
    private static double fixedPositionLongitude;
    private static double maxRange = 0; // 0 == unlimited range
    private static Args clArgs;

    public static void main(String[] args) {
        // process the CL args..
        try {
            // Process args from commandline
            setupApplication(args);

            // start thread (POC)
            CacheManager cashbash = new CacheManager(ActiveContacts);

            // start the Solr publishing thread
            ContactIndexer indexer = new ContactIndexer(ActiveContacts,clArgs.getSolrHostname(), clArgs.getSolrPort(), clArgs.getSolrCollectionName());
            if(clArgs.hasDryRun()){
                indexer.dryRun();
            }

            // Setup stream reader
            SBSTrafficSocket sbsTrafficSocket = new SBSTrafficSocket(clArgs.getDump1090Hostname(), clArgs.getDump1090Port());

            // process traffic feed from  sbsTrafficSocket
            processMessages(ActiveContacts, sbsTrafficSocket);

            // shutdown the threads
            cashbash.shutdown();

        } catch (Exception e) {
            System.err.printf("\n** Fatal Error -- %s\n", e.toString());
            e.printStackTrace();
            System.exit(9);
        }

    }

    /**
     * Handle Application setup
     * <p>
     * Compute resolution from settings, if provided
     *
     * @param args
     * @throws ParseException
     */
    private static void setupApplication(String[] args) throws ParseException {
        // store into args var
        clArgs = new Args(args);

        debug = clArgs.hasDebug();

    }


    /**
     * Read simple text message strings from the port connection stream
     * <p>
     * Parse into object, check ICAO to see if it had been seen before, and if not, add to in-mem dataset, otherwise
     * merge messages into a unified model -- at some point the model would be complete enough to express a position,
     * callsign and bearing.
     *
     * @param airTraffic
     * @param sbsTrafficSocket
     * @throws IOException
     */
    private static void processMessages(Map<String, NewContact> airTraffic, SBSTrafficSocket sbsTrafficSocket) throws IOException {

        BufferedReader bis = new BufferedReader(new InputStreamReader(sbsTrafficSocket.getSocket().getInputStream()));
        String dump1090Message;

        while ((dump1090Message = bis.readLine()) != null) {

            // init flag to determine writes to <STDOUT>
            boolean publishContact = false;
            boolean publishInRageContact = false;

            // convert the CSV message string into an AircraftSBS message object
            NewContact contact = new NewContact(dump1090Message);

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
                contact.newContact(); // set flag that contact is new
                airTraffic.put(icao, contact);
                // it not operating in quiet mode, announce the new contact
            }

            // get rebotic..
            //System.out.println(airTraffic.get(icao).toJSONLite());

            if(debug){
                Announce(airTraffic.get(icao));
            }
        }

        // done, close socket
        sbsTrafficSocket.getSocket().close();

    }

    /**
     * Announce contact to STDOUT
     * <p>
     * This will work as preliminary data capture
     *
     * @param contact
     */
    private static void Announce(NewContact contact) {
        //String contactData = contact.announceContactTerse(true);
        //if(contactData != null) {
        //    System.out.printf("\t[%03d] %s\n", ActiveContacts.size(), contactData);
        //}
    }

}
