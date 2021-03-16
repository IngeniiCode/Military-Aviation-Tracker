package com.daviddemartini.avtracker.services.message_parse_service.console;

import com.daviddemartini.avtracker.services.message_parse_service.datamodel.AircraftSBS;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Manages the in-mem cache to collect old contacts and remove from dataset,
 * aka a garbage collector
 */
public class ConsoleLog {

    private static Map<String, AircraftSBS> constactsCache;
    private static Runnable contactLogger;
    private volatile boolean shutdown = false;
    private static int resolution;

    public ConsoleLog(Map<String, AircraftSBS> constactsCache,int resolution) {
        // save cache pointer
        this.constactsCache = constactsCache;
        this.resolution = resolution;

            // runnable thread wrapper
            contactLogger = new Runnable() {
                // runner
                public void run() {
                    logCurrentContacts();
                }
            };

            // start collecting
            runCacheBasher();

    }

    /**
     * Threaded cache basher
     */
    public void runCacheBasher() {
        // announce, no more frequently than ever 1 second
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(contactLogger, 10, (resolution>0) ? resolution : 1, TimeUnit.SECONDS);

    }

    /**
     * Look at all contacts in memory and any that are more than interval old,
     * delete from the cache
     */
    private static void logCurrentContacts() {

        try {
            // Iterate over all the elements
            Iterator<String> contactIterator = constactsCache.keySet().iterator();
            while (contactIterator.hasNext()) {
                String icoaId = contactIterator.next();
		String contactInfo = constactsCache.get(icoaId).announceContactTerse();
		if(contactInfo != null) {
                	System.out.printf("\t\t[%03d] : %s\n", constactsCache.size(), constactsCache.get(icoaId).announceContactTerse());
		}
            }
        } catch (Exception e) {
            System.err.printf("** ConsoleLogger -- DATA LOGGING ERROR %s\n",e.getMessage());
            return;
        }
    }

    // shutdown hook
    public void shutdown() {
        shutdown = true;
    }

}
