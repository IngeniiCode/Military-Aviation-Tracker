package com.daviddemartini.avtracker.services.faa.model_reference.util;

import java.util.Map;


public class Settings {

    protected Settings systemEnv;
    protected String faaDataFile = "./data/ACFTREF.txt";
    protected String publisherHostname = "localhost";
    protected int publisherPort = 9201;

    /**
     * Ingest system settings and populate properties from expected values
     */
    public Settings() {
        Map<String, String> systemEnv = System.getenv();
        // import systemSettings
        faaDataFile = (systemEnv.containsKey("MILTRACK_ACFTREF_FILE"))
                ? systemEnv.get("MILTRACK_ACFTREF_FILE").trim() : faaDataFile;
        publisherHostname = (systemEnv.containsKey("MILTRACK_ACFTREF_HOST"))
                ? systemEnv.get("MILTRACK_ACFTREF_HOST").trim() : publisherHostname;
        publisherPort = (systemEnv.containsKey("MILTRACK_ACFTREF_PORT"))
                ? Integer.parseInt(System.getenv("MILTRACK_ACFTREF_PORT").trim()) : publisherPort;

    }


    /**
     * ** GETTERS **
     */

    public String getFaaDataFile() {
        return faaDataFile;
    }

    public String getPublisherHostname() {
        return publisherHostname;
    }

    public int getPublisherPort() {
        return publisherPort;
    }

}
