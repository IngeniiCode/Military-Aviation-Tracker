package com.daviddemartini.avtracker.services.new_contacts_pipeline.comms.solr;

import com.daviddemartini.avtracker.services.new_contacts_pipeline.datamodel.NewContact;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;
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
    private static String solrIndexer;
    private static URI solrURI;
    private volatile boolean shutdown = false;
    private static boolean dryrun = false;
    private static final int commitWithin = 500;
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public ContactIndexer(Map<String, NewContact> constactsCache, String solrHostname, int solrPort, String solrCollection) {
        new ContactIndexer(constactsCache, solrHostname, solrPort, solrCollection,false);
    }
    public ContactIndexer(Map<String, NewContact> constactsCache, String solrHostname, int solrPort, String solrCollection, boolean dryrun) {
        // save cache pointer
        this.constactsCache = constactsCache;
        this.solrIndexer = String.format("%s:%d/solr/%s", solrHostname, solrPort, solrCollection);
        this.dryrun = dryrun;

        try {
            solrURI = new URI(String.format("http://%s/update?_=1615905561560&commitWithin=%d&overwrite=false&wt=json",
                    solrIndexer,
                    commitWithin));

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
            executor.scheduleAtFixedRate(contactPublisher, 5, 10, TimeUnit.SECONDS);
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
            //String[] contacts = constactsCache.keySet().toArray(new String[constactsCache.size()]);
            System.out.printf("Checking cache [%d] for new contacts",constactsCache.size());
            Map<String,String> newContacts = new HashMap<>();

            // loop through list of strings
            for(String icaoId: constactsCache.keySet().toArray(new String[constactsCache.size()])){
                // verify that contact has not been cachebashed before attempting to index
                if(constactsCache.containsKey(icaoId)) {
                    NewContact contact = constactsCache.get(icaoId);
                    if (contact.isNewContact()) {
                        // check to see if it has a Callsign set
                        if (contact.hasCallsign()) {
                            // add to document stack
                            newContacts.put(icaoId,contact.toJSONLite());
                        }
                    }
                }
            }

            // check to see if any new contacts need to be published
            if(newContacts.size() > 0){
                System.out.printf("\t%d New Contacts\n",newContacts.size());
                StringJoiner solrDocs = new StringJoiner(",");
                // build document
                for(String icaoId: newContacts.keySet().toArray(new String[newContacts.size()])){
                    System.out.printf(" ++ NEW:  %s\n",newContacts.get(icaoId).toString());
                    solrDocs.add(newContacts.get(icaoId));
                }
                // clear the docs
                if(postToSolr(solrDocs.toString())){
                    for(String icaoId: newContacts.keySet().toArray(new String[newContacts.size()])){
                        if (constactsCache.containsKey(icaoId)) {
                            constactsCache.get(icaoId).clearNewContact();
                        }
                    }
                }
            }
            else {
                // no new contacts to record
                System.out.printf("\n"); // closes open printf
            }

        } catch (Exception e) {
            // ToDo -- improve exception handing here
            System.err.println("publishNewContacts EXCEPTION: " + e.getCause().getMessage());
        }
    }

    /**
     * Post to the Solr Collection
     *
     * @param solrDocs
     */
    private static boolean postToSolr(String solrDocs) {

        try {

            // if dry run flag set, perform all operations except post to Solr.
            if(dryrun){
                System.out.println("\n** DR ** " + solrDocs);
                return true;
            }

            String reformatted = String.format("[%s]", solrDocs);
            HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(reformatted);

            HttpRequest request = HttpRequest.newBuilder()
                    .POST(bodyPublisher)
                    .uri(solrURI)
                    .setHeader("User-Agent", "IngeniiGroup Contacts Publisher") // add request header
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                String badResonse = String.format("ERR-CODE:%d Failed to Index @ %s", response.statusCode(), solrIndexer);
                System.err.println(badResonse);
                return false;
            }

            // good indexing operation
            return true;

        } catch (Exception e) {
            System.err.printf("Failed to Index @ %s  -- %s\n", solrIndexer, e.getMessage());
            return false;
        }

    }

    public void dryRun(){
        this.dryrun = true;
    }

    // shutdown hook
    public void shutdown() {
        shutdown = true;
    }

}
