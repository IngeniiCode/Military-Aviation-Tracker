package com.daviddemartini.avtracker.services.new_contacts_pipeline.comms.solr;

import com.daviddemartini.avtracker.services.new_contacts_pipeline.datamodel.NewContact;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Publishes the in-mem cache to solr when a new contact received and sufficient message data
 * has been collected
 */
public class ContactIndexer {

    private static Map<String, NewContact> constactsCache;
    private static Runnable contactPublisher;
    private static String solrCollection;
    private static URI solrURI;
    private volatile boolean shutdown = false;
    private static final int commitWithin = 500;

    public ContactIndexer(Map<String, NewContact> constactsCache, String solrHostname, int solrPort, String solrCollection) {
        // save cache pointer
        this.constactsCache = constactsCache;
        this.solrCollection = String.format("%s:%d/solr/%s", solrHostname, solrPort, solrCollection);
        String solrUpdate = String.format("http://%s/update?_=1615905561560&commitWithin=%d&overwrite=false&wt=json",
                solrCollection,
                commitWithin);

        try {
            solrURI = new URI(solrUpdate);

            // runnable thread wrapper
            contactPublisher = new Runnable() {
                // runner
                public void run() {
                    publishNewContacts();
                }
            };

            // start collecting
            runContactPublisher();
        } catch (Exception e) {
            // ToDo -- improve exception handing here
            System.err.println("ContactIndexer EXCEPTION: " + e.getCause().getMessage());
        }
    }

    /**
     * Threaded Contacts Index Publisher
     */
    public void runContactPublisher() {

        try {
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executor.scheduleAtFixedRate(contactPublisher, 1, 5, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.err.println("runContactPublisher EXCEPTION: " + e.getCause().getMessage());
        }
    }

    /**
     * Look at all contacts in memory and any that are marked new and have sufficent data, they are published
     */
    private static void publishNewContacts() {

        // Iterate over all the elements
        try {
            Iterator<String> contactIterator = constactsCache.keySet().iterator();
            while (contactIterator.hasNext()) {
                String icoaId = contactIterator.next();
                NewContact contact = constactsCache.get(icoaId);
                if (contact.isNewContact()) {
                    // check to see if it has a Callsign set
                    if (contact.hasCallsign()) {
                        // publish
                        System.out.println("NEW: " + contact.toJSONLite());
                        postToSolr(contact.toJSONLite());
                        constactsCache.get(icoaId).clearNewContact();
                    }

                }
            }
        } catch (Exception e) {
            // ToDo -- improve exception handing here
            System.err.println("publishNewContacts EXCEPTION: " + e.getCause().getMessage());
        }
    }

    /**
     * Post to the Solr Collection
     *
     * @param Json
     */
    private static void postToSolr(String Json) {

        try {
            String reformatted = String.format("[%s]", Json);
            HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(reformatted);
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().POST(bodyPublisher);
            requestBuilder.uri(solrURI);
            // add headers
            requestBuilder.header("User-Agent", "IngeniiGroup Contacts Publisher");
            requestBuilder.setHeader("Content-Type", "application/json");
            // build
            HttpRequest request = requestBuilder.build();
            // post data to solr
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                String badResonse = String.format("ERR-CODE:%d Failed to Index @ %s", response.statusCode(), solrCollection);
                System.err.println(badResonse);
            }
        } catch (Exception e) {
            System.err.printf("Failed to Index @ %s  -- %s\n", solrCollection, e.getMessage());
        }

    }

    // shutdown hook
    public void shutdown() {
        shutdown = true;
    }

}
