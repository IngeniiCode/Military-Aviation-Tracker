
    private String icao;
    private String callsign;
    private Boolean milCallsign;
    private String detectedTimestamp;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
    private int squawkInt;
    // used for timestamping reports
    private long lastReport;
    // turn up/down frequency of position updates to publishers
    private int resolution;

