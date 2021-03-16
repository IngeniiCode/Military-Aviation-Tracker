package com.daviddemartini.avtracker.services.new_contacts_pipeline.util;


import java.util.Map;

public class Settings  {

    protected String dump1090Hostname  = "localhost";
    protected String solrHostname = "localhost";
    protected String solrCollectionName = "aviation_contacts";
    protected String resolution = "all";
    protected int dump1090Port = 30003;
    protected int solrPort = 8983;

    /**
     * Ingest system settings and populate properties from expected values
     */
    public Settings(){
        Map<String, String> systemEnv = System.getenv();
        // import systemSettings
        dump1090Hostname = (systemEnv.containsKey("MILTRACK_DUMP1090_HOST"))
                ? systemEnv.get("MILTRACK_DUMP1090_HOST").trim() : dump1090Hostname;
        dump1090Port = (systemEnv.containsKey("MILTRACK_DUMP1090_PORT"))
                ? Integer.parseInt(System.getenv("MILTRACK_DUMP1090_PORT").trim()) : dump1090Port;
        solrHostname = (systemEnv.containsKey("MILTRACK_SOLR_HOST"))
                ? systemEnv.get("MILTRACK_SOLR_HOST").trim() : solrHostname;
        solrPort = (systemEnv.containsKey("MILTRACK_SOLR_PORT"))
                ? Integer.parseInt(System.getenv("MILTRACK_SOLR_PORT").trim()) : solrPort;
        solrCollectionName = (systemEnv.containsKey("MILTRACK_SOLR_COLLECTION"))
                ? systemEnv.get("MILTRACK_SOLR_COLLECTION").trim() : solrCollectionName;
        resolution = (systemEnv.containsKey("MILTRACK_RESOLUTION"))
                ? systemEnv.get("MILTRACK_RESOLUTION").toLowerCase().trim() : resolution;
    }

    /**
     * GETTERS
     *
     * @return
     */

    public String getDump1090Hostname() {
        return dump1090Hostname;
    }

    public String getSolrHostname() {
        return solrHostname;
    }

    public String getSolrCollectionName() {
        return solrCollectionName;
    }

    public String getResolution() {
        return resolution;
    }

    public int getDump1090Port() {
        return dump1090Port;
    }

    public int getSolrPort() {
        return solrPort;
    }


}
