package com.daviddemartini.avtracker.services.faa.model_reference.util;

import com.daviddemartini.avtracker.services.faa.model_reference.util.Settings;
import org.apache.commons.cli.*;

public class Args extends Settings {

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
        options.addOption(Option.builder("a")
                .longOpt("faa-models")
                .desc("FAA Model Reference File Path (ACFTREF.txt)")
                .hasArg()
                .build());

        options.addOption(Option.builder("b")
                .longOpt("svc-host")
                .desc("Service Hostname / IP for serviced binding")
                .hasArg()
                .build());

        options.addOption(Option.builder("c")
                .longOpt("svc-port")
                .desc("Service port")
                .hasArg()
                .build());

        // create the parser and the commadline interface
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = new DefaultParser().parse(options, args);

        // check for parameters
        if (cmd.hasOption("faa-models")) {
            faaDataFile = cmd.getOptionValue("faa-models");
        }
        if (cmd.hasOption("svc-host")) {
            publisherHostname = cmd.getOptionValue("svc-host");
        }
        if (cmd.hasOption("svc-port")) {
            publisherPort = Integer.parseInt(cmd.getOptionValue("svc-port"));
        }

    }


}
