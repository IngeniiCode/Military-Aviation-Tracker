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
    private static Args clArgs;
    private static boolean running = false;

    public static void main(String[] args) {
        // process the CL args..
        try {
            // Process args from commandline
            clArgs = new Args(args);

            // start the Solr publishing thread
            ContactIndexer indexer = new ContactIndexer(ActiveContacts,clArgs.getSolrHostname(), clArgs.getSolrPort(), clArgs.getSolrCollectionName(),clArgs.hasDryRun());
            //if(clArgs.hasDryRun()){
            //    indexer.dryRun();
            //}

            // start thread (POC)
            CacheManager cashbash = new CacheManager(ActiveContacts);

            // process traffic feed from  sbsTrafficSocket
            processMessages(ActiveContacts);

            // shutdown the threads
            cashbash.shutdown();

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
     * @throws IOException
     */
    private static void processMessages(Map<String, NewContact> airTraffic) throws Exception {

        running = true;
        while(running) {
            System.out.printf("Opening Connection: %s:%d\n",clArgs.getDump1090Hostname(),clArgs.getDump1090Port());
            // Setup stream reader

            SBSTrafficSocket sbsTrafficSocket = new SBSTrafficSocket(clArgs.getDump1090Hostname(), clArgs.getDump1090Port());
            BufferedReader trafficReader = sbsTrafficSocket.getBufferedReader();
            String dump1090Message = "";

            while ((dump1090Message = trafficReader.readLine()) != null) {

                // convert the CSV message string into an AircraftSBS message object
                NewContact contact = new NewContact(dump1090Message);

                // Get the ICAO Hex present in every message
                String icao = contact.getIcao();

                // check to see if this is a known or new contact
                if (airTraffic.containsKey(icao)) {
                    airTraffic.get(icao).merge(contact);
                } else {
                    contact.newContact(); // set flag that contact is new
                    airTraffic.put(icao, contact);
                }

                if (clArgs.hasDebug()) {
                    Announce(airTraffic.get(icao));
                }
            }

            // socket has closed
            System.out.println("\nMessage Socket Closed\n");
            sbsTrafficSocket.getSocket().close();
        }

    }

    /**
     * Announce contact to STDOUT
     *
     * This will work as preliminary data capture
     *
     * @param contact
     */
    private static void Announce(NewContact contact) {
        try {
            System.out.printf("\t[%03d] %s\n", ActiveContacts.size(), contact.toJSONLite());
        } catch (Exception e) {
            System.err.println("Announcement Error: " + e.getMessage());
        }
    }

}
