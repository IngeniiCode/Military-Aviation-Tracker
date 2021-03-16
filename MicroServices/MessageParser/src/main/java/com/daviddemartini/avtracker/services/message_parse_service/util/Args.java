package com.daviddemartini.avtracker.services.message_parse_service.util;

import org.apache.commons.cli.*;

public class Args extends Settings {

    private boolean hasLatitude = false;
    private boolean hasLongitude = false;
    private boolean milOnly = false;
    private boolean quiet = false;
    private boolean debug = false;

    /**
     * Constructor
     *
     * @param args
     * @throws ParseException
     */
    public Args(String[] args) throws ParseException {

        Options options = new Options();

        // define what optiosn are provided
        options.addOption(Option.builder("m")
                .longOpt("myhost")
                .desc("Publishing hostname")
                .hasArg()
                .build());

        options.addOption(Option.builder("h")
                .longOpt("host")
                .desc("Dump1090 Socket hostname")
                .hasArg()
                .build());

        options.addOption(Option.builder("p")
                .longOpt("port")
                .desc("Dump1090 Socket port")
                .hasArg()
                .build());

        options.addOption(Option.builder("v")
                .longOpt("lat")
                .desc("Position Latitude")
                .hasArg()
                .build());

        options.addOption(Option.builder("l")
                .longOpt("lon")
                .desc("Position Longitude")
                .hasArg()
                .build());

        options.addOption(Option.builder("r")
                .longOpt("range")
                .desc("Maximum Range to Report Contacts")
                .hasArg()
                .build());

        options.addOption(Option.builder("y")
                .longOpt("resolution")
                .desc("Logging Resolution  All|High|Normal|Low")
                .hasArg()
                .build());

        options.addOption(Option.builder("m")
                .longOpt("milonly")
                .desc("Only log military contacts")
                .build());

        options.addOption(Option.builder("q")
                .longOpt("quiet")
                .desc("Quieter operation - do not announce new contacts")
                .build());

        options.addOption(Option.builder("d")
                .longOpt("debug")
                .desc("Dump all contacts to STDOUT for debugging")
                .build());

        // create the parser and the commadline interface
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = new DefaultParser().parse(options, args);

        // check for parameters
        if (cmd.hasOption("myhost")) {
            publisherHostname = cmd.getOptionValue("myhost");
        }
        if (cmd.hasOption("host")) {
            dump1090Hostname = cmd.getOptionValue("host");
        }
        if (cmd.hasOption("port")) {
            dump1090Port = Integer.parseInt(cmd.getOptionValue("port"));
        }
        if (cmd.hasOption("lat")) {
            hasLatitude = true;
            fixedPositionLatitude = Double.parseDouble(cmd.getOptionValue("lat"));
        }
        if (cmd.hasOption("lon")) {
            hasLongitude = true;
            fixedPositionLongitude = Double.parseDouble(cmd.getOptionValue("lon"));
        }
        if (cmd.hasOption("range")) {
            maxDetectionRange = Double.parseDouble(cmd.getOptionValue("range"));
        }
        if (cmd.hasOption("milonly")) {
            // only announce contacts that exhibit a military like callsign
            milOnly = true;
        }
        if (cmd.hasOption("quiet")) {
            // don't announce new contacts
            quiet = true;
        }
        if (cmd.hasOption("debug")) {
            // don't announce new contacts
            debug = true;
        }
        if (cmd.hasOption("resolution")) {
            resolution = cmd.getOptionValue("resolution").toLowerCase().trim();
        }

    }

    /**
     * Boolean test to see if the position values are valid, or NaNs
     *
     * @return
     */
    public boolean hasPosition() {
        return (hasLatitude && hasLatitude);
    }

    /**
     * Boolean test to see if the display mil only flag has been set.
     *
     * @return
     */
    public boolean hasMilOnly() {
        return milOnly;
    }

    public boolean hasQuiet() {
        return quiet;
    }

    public boolean hasDebug() {
        return debug;
    }
}
