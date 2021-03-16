package com.daviddemartini.avtracker.services.new_contacts_pipeline.datamodel;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Manages the in-mem cache to collect old contacts and remove from dataset,
 * aka a garbage collector
 */
public class CacheManager {

    private static Map<String, NewContact> constactsCache;
    private static long gcAgeOffset = 180000; // contacts over 3 min. old are removed (milisecons)
    private static Runnable cacheBasher;
    private volatile boolean shutdown = false;

    public CacheManager(Map<String, NewContact> constactsCache) {
        // save cache pointer
        this.constactsCache = constactsCache;

        // runnable thread wrapper
        cacheBasher = new Runnable() {
            // runner
            public void run() {
                collectGarbage();
            }
        };

        // start collecting
        runCacheBasher();
    }

    /**
     * Threaded cache basher
     */
    public void runCacheBasher() {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(cacheBasher, 10, 60, TimeUnit.SECONDS);

    }

    /**
     * Look at all contacts in memory and any that are more than interval old,
     * delete from the cache
     */
    private static void collectGarbage() {
        long collectorCutoff = System.currentTimeMillis() - gcAgeOffset;
        // Iterate over all the elements
        try {
            Iterator<String> contactIterator = constactsCache.keySet().iterator();
            while (contactIterator.hasNext()) {
                String icoaId = contactIterator.next();
                // Check if Value associated with Key is ODD
                if ((long) constactsCache.get(icoaId).getLastReport() < collectorCutoff) {
                    // Remove the element
                    System.out.printf("\t\t ** Lost Contact with %8s (%6s) **\n",constactsCache.get(icoaId).getCallsign(), icoaId);
                    contactIterator.remove();
                }
            }
        } catch (Exception e) {
            System.err.printf("** CacheManager -- DATA LOGGING ERROR %s\n",e.getMessage());
            return;
        }
    }

    // shutdown hook
    public void shutdown() {
        shutdown = true;
    }

}
