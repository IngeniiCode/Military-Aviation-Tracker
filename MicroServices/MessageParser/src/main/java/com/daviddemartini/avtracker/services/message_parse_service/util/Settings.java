package com.daviddemartini.avtracker.services.message_parse_service.util;


import java.util.Map;

public class Settings  {

    protected Settings systemEnv;
    protected String publisherHostname = "localhost";
    protected String dump1090Hostname  = "localhost";
    protected String resolution = "all";
    protected int dump1090Port = 30003;
    protected Double fixedPositionLatitude;
    protected Double fixedPositionLongitude;
    protected Double maxDetectionRange = 200.0; // it's not expected that the receiver will hear beyond 200 miles


    /**
     * Ingest system settings and populate properties from expected values
     */
    public Settings(){
        Map<String, String> systemEnv = System.getenv();
        // import systemSettings
        publisherHostname = (systemEnv.containsKey("MILTRACK_PUBLISHER_HOST"))
                ? systemEnv.get("MILTRACK_PUBLISHER_HOST").trim() : publisherHostname;
        dump1090Hostname = (systemEnv.containsKey("MILTRACK_DUMP1090_HOST"))
                ? systemEnv.get("MILTRACK_DUMP1090_HOST").trim() : dump1090Hostname;
        dump1090Port = (systemEnv.containsKey("MILTRACK_DUMP1090_PORT"))
                ? Integer.parseInt(System.getenv("MILTRACK_DUMP1090_PORT").trim()) : dump1090Port;
        fixedPositionLatitude = (systemEnv.containsKey("MILTRACK_FIXED_LATITUDE"))
                ? Double.parseDouble(systemEnv.get("MILTRACK_FIXED_LATITUDE").trim()) : null;
        fixedPositionLongitude = (systemEnv.containsKey("MILTRACK_FIXED_LONGITUDE"))
                ? Double.parseDouble(systemEnv.get("MILTRACK_FIXED_LONGITUDE").trim()) : null;
        maxDetectionRange = (systemEnv.containsKey("MILTRACK_MAX_DETECTION_RANGE"))
                ? Double.parseDouble(systemEnv.get("MILTRACK_MAX_DETECTION_RANGE").trim()) : maxDetectionRange;
        resolution = (systemEnv.containsKey("MILTRACK_RESOLUTION"))
                ? systemEnv.get("MILTRACK_RESOLUTION").toLowerCase().trim() : resolution;
    }

    public Settings getSystemEnv() {
        return systemEnv;
    }

    public String getPublisherHostname() {
        return publisherHostname;
    }

    /**
     * get the socket hostname
     *
     * @return
     */
    public String getDump1090Hostname() throws Exception {
        if(this.dump1090Hostname != null) {
            return dump1090Hostname;
        }
        else {
            Exception e = new IllegalArgumentException("Invalid or missing socket address/url paramter --url");
            throw e ;
        }
    }

    /**
     * get the socket port
     *
     * @return
     */
    public int getDump1090Port(){
        return dump1090Port;
    }

    public Double getFixedPositionLatitude() {
        return fixedPositionLatitude;
    }

    public Double getFixedPositionLongitude() {
        return fixedPositionLongitude;
    }

    public Double getMaxDetectionRange() {
        return maxDetectionRange;
    }

    public String getResolution() {
        return resolution;
    }

}
